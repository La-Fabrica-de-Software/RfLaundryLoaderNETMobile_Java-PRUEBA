package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTaquilla
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.LecturaPrendaClienteSubCliente
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.Prenda
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.PrendaCliente
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.PrendaSubCliente
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaClienteSubClienteByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla.GetTaquillaByDescripcionCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla.GetTaquillasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.InitConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.util.EnteredBarcodeStatus
import com.rscja.deviceapi.Barcode1D
import com.rscja.deviceapi.exception.ConfigurationException;
import com.rscja.deviceapi.RFIDWithUHFUART
import com.rscja.deviceapi.entity.UHFTAGInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AsignacionPrendasViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getConfiguracion: GetConfiguracion,
    private val getPrendaClienteSubClienteByTagCommon: GetPrendaClienteSubClienteByTagCommon,
    private val initConnectionRemote: InitConnectionRemote,
    private val testConnectionRemote: TestConnectionRemote,
    private val getTaquillaByDescripcionCommon: GetTaquillaByDescripcionCommon,
    private val getTaquillasCommon: GetTaquillasCommon
) : ViewModel() {

    //region Variables

    //Reader
    private var _reader: RFIDWithUHFUART? = null
    private var _inventoryFlag = 0
    private var _triggerPressed = false

    //Scanner
    private lateinit var _scanner: Barcode1D
    private var _isScanning: Boolean = false
    private var _maxScanningTime: Long = 2500L
    private var _maxAfterTipingWaitingBarcodeTime: Long = 1000L

    //Tags
    private var _listOfTags = listOf<String>()
    private var _totalTags: Int = 0

    //Barcode
    private var _barcode = mutableStateOf("")
    val barcode: State<String> = _barcode

    //Prendas
    private var _listOfPrendasEncontradas = listOf<Prenda>()
    private var _listOfPrendasDesconocidas = listOf<Prenda>()
    private var _listOfPrendasEncontradasYUbicadas = listOf<PrendaCliente>()

    //Taquilla
    private var _taquilla = MasterTaquilla()

    //State
    private val _uiState = MutableStateFlow(AsignacionPrendasState())
    val uiState = _uiState.asStateFlow()

    //Jobs
    private var testConnectionJob: Job? = null
    private var barcodeJob: Job? = null
    private var lecturaPrendasJob: Job? = null

    //Time
    private var _verifyTagTime = 0L
    private var _verifyBarcodeTime = _maxScanningTime
    private var _afterTipingWaitingBarcodeTime = _maxAfterTipingWaitingBarcodeTime

    //endregion

    init {
        initReader(1)
        initScanner()

        onEvent(AsignacionPrendasEvent.TestConnection)

        Timer().scheduleAtFixedRate( object : TimerTask() {
            override fun run() {
                onEvent(AsignacionPrendasEvent.UpdateData)
                if (_verifyTagTime > 10000 &&
                    _listOfTags.isNotEmpty() &&
                    (_listOfTags.count() - (_listOfPrendasEncontradas.count() + _listOfPrendasDesconocidas.count())) > 0) {

                    processNotVerifiedTags()
                }
                _verifyTagTime += 250

                if (_isScanning) {
                    if (_verifyBarcodeTime < _maxScanningTime) {
                        scan()
                    } else {
                        stopScan(false)
                    }
                }
                _verifyBarcodeTime += 250

                if (_afterTipingWaitingBarcodeTime < _maxAfterTipingWaitingBarcodeTime) {
                    _afterTipingWaitingBarcodeTime += 250
                }
            }
        }, 0, 250)
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - Reader

    //region Reader
    private fun initReader(inventoryFlag: Int) {
        stopInventory()
//        freeReader()
        _inventoryFlag = inventoryFlag

        try {
            _reader = RFIDWithUHFUART.getInstance()
            println("+++++ initReader - Reader instance captured +++++")
        } catch (e: Exception) {
            println("----- initReader - Reader instance exception: ${e.message}")
            return
        }
        if (_reader != null) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    try {
                        if (_inventoryFlag == 1) { _reader?.setEPCMode() }
                        _reader?.init(context)
                        println("+++++ initReader - Reader init OK +++++")
                    } catch (e: Exception) {
                        println("----- initReader - Reader init exception: ${e.message}")
                    }
                }
            }
        }
    }
    //endregion

    //region Inventory
    fun startInventory() {
        _verifyTagTime = 0
        _triggerPressed = true
        when (_inventoryFlag) {
            0 -> {
                val res: UHFTAGInfo? = _reader?.inventorySingleTag()
                if (res != null) {
                    viewModelScope.launch(Dispatchers.IO) {
                        processEpc(res.epc)
                    }
                }
            }
            1 -> {
                if (_reader?.startInventoryTag() == true) {
                    println("----- Start inventory")
                    viewModelScope.launch(Dispatchers.IO) {
                        getContinuousEpc()
                    }
                } else {
                    stopInventory()
                }
            }
        }
    }
    fun stopInventory() {
        try {
            _reader?.stopInventory()
            println("+++++ stopInventory OK")
        } catch (e: Exception) {
            println("----- stopInventory Exception: ${e.message}")
        }
    }
    //endregion

    //region Processing and place tag
    private fun getContinuousEpc() {
        var res: UHFTAGInfo?
        while (_triggerPressed) {
            res = _reader?.readTagFromBuffer()
            if (res != null) {
                processEpc(res.epc)
            }
        }
    }
    private fun processEpc(epc: String){
        _totalTags++
        val tag = if (epc.count() > 16) {epc.takeLast(16)} else {epc}
        if (!_listOfTags.contains(tag)) {
            _listOfTags = _listOfTags + tag
            viewModelScope.launch(Dispatchers.Default) {
                verifyTag(tag)
            }
        }
        onEvent(AsignacionPrendasEvent.UpdateData)
    }
    private suspend fun verifyTag(tag: String) {
        try {
            val prenda = getPrendaClienteSubClienteByTagCommon(tag, config = getConfiguracion())
            viewModelScope.launch(Dispatchers.Main) {
                placeTag(prenda)
                withContext(Dispatchers.Main) {
                    onEvent(AsignacionPrendasEvent.UpdateData)
                }
            }
        } catch (e: Exception) {
            showError(e.message.toString())
        }
    }
    private fun placeTag(prenda: LecturaPrendaClienteSubCliente) {
        _verifyTagTime = 0
        if (prenda.idPrenda > 0) {
            val newClientePrenda = PrendaCliente(prenda.idCliente, prenda.nombreCliente, mutableListOf(), isBorrado = prenda.clienteBorrado)
            val newSubClientePrenda = PrendaSubCliente(prenda.idSubCliente, prenda.nombreSubCliente, mutableListOf(), isBorrado = prenda.subClienteBorrado)
            val newPrenda = Prenda(prenda.idPrenda, prenda.nombrePrenda, prenda.talla, prenda.tag, true, prenda.isBorrado, true, newClientePrenda.idCliente, prenda.clienteBorrado, newSubClientePrenda.idSubCliente, prenda.subClienteBorrado, prenda.idModeloPrenda, prenda.nombreModeloPrenda, prenda.lastMovFecha)

            if (_listOfPrendasEncontradas.none { it.tag == prenda.tag }) {
                _listOfPrendasEncontradas = _listOfPrendasEncontradas + newPrenda
                if (_listOfPrendasEncontradasYUbicadas.filter { it.idCliente == prenda.idCliente }.size == 1) {
                    if (_listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente.filter { it.idSubCliente == prenda.idSubCliente }.size == 1) {
                        _listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente.first { it.idSubCliente == prenda.idSubCliente}.listadoPrenda += newPrenda
                    } else {
                        _listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente += newSubClientePrenda
                        _listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente.first { it.idSubCliente == prenda.idSubCliente}.listadoPrenda += newPrenda
                    }
                } else {
                    _listOfPrendasEncontradasYUbicadas = _listOfPrendasEncontradasYUbicadas + newClientePrenda
                    _listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente += newSubClientePrenda
                    _listOfPrendasEncontradasYUbicadas.first { it.idCliente == prenda.idCliente }.listadoPrendaSubCliente.first { it.idSubCliente == prenda.idSubCliente}.listadoPrenda += newPrenda
                }
            }
        } else {
            if (_listOfPrendasDesconocidas.none { it.tag == prenda.tag }) {
                val prendaItem = Prenda(tag = prenda.tag, isExiste = false, isBorrado = false, isVerificado = true)
                _listOfPrendasDesconocidas = _listOfPrendasDesconocidas + prendaItem
            }
        }
    }
    //endregion

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - Scanner

    //region Scanner
    private fun initScanner() {
        try {
            _scanner = Barcode1D.getInstance()
            _scanner.setTimeOut(500)
            println("+++++ initScanner - Scanner instance created +++++")
        } catch (e: ConfigurationException) {
            println("----- initScanner - Scanner instance exception: ${e.message}")
            return
        }


    }
    private fun freeScanner() {
        _scanner?.close()
    }
    //endregion

    //region Scan
    fun startScan() {
        _scanner.open(context)
        if (!_isScanning) {
            println("* * * * * Start/open scan")
            _isScanning = true
            _verifyBarcodeTime = 0
        } else {
            println("* * * * * Already scanning")
        }
    }
    private fun scan() {
        println("* * * * * Scanning")
        _barcode.value = _scanner.scan().toString()
        if (_barcode.value.isNotEmpty()) {
            println("* * * * * Barcode OK")
            onEvent(AsignacionPrendasEvent.EnteredBarcode(_barcode.value))
            stopScan(true)
        }
    }
    private fun stopScan(succesfully: Boolean) {
        println("* * * * * Stop scanning")
        if (!succesfully) {
            onEvent(AsignacionPrendasEvent.EnteredBarcode("error"))
        }
        _isScanning = false
        _scanner.close()
    }
    //endregion

    //region Events
    fun onEvent(event: AsignacionPrendasEvent) {
        when(event) {
            is AsignacionPrendasEvent.TestConnection -> {
                testConnectionJob?.cancel()
                testConnectionJob = testConnectionJob()
            }
            is AsignacionPrendasEvent.UpdateData -> {
                lecturaPrendasJob?.cancel()
                lecturaPrendasJob = updateLecturaPrendasJob()
            }
            is AsignacionPrendasEvent.CleanAllData -> {
                cleanAll()
            }
            is AsignacionPrendasEvent.ShowMessage -> {
            }
            is AsignacionPrendasEvent.ShowLoadingWithMessage -> {
            }
            is AsignacionPrendasEvent.EnteredBarcode -> {
                _uiState.value = _uiState.value.copy(barcode = event.value)
                _afterTipingWaitingBarcodeTime = 0

                barcodeJob?.cancel()
                barcodeJob = findBarcodeJob(event.value)
            }
            is AsignacionPrendasEvent.ShowAssignmentInitialDialog -> {
                showAssignmentInitialDialog(event.showDialog)
            }
            is AsignacionPrendasEvent.GenerateAsignment -> {

            }
            is AsignacionPrendasEvent.ShowAssignmentDoneDialog -> {
                showAssignmentDoneDialog(event.showDialog)
            }
            else -> {}
        }
    }
    //end region

    //region Jobs
    private fun testConnectionJob(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                initConnectionRemote(getConfiguracion())
                val testConnResult = testConnectionRemote()
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(
                        connectionStatus = testConnResult,
                        showLoadingMessage = false,
                        showMessage = false
                    )
                }
            } catch (e: Exception) {
                println("----- AsignacionPrendas testConnectionJob exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(
                        connectionStatus = false,
                        showLoadingMessage = false,
                        showMessage = false
                    )
                }
            }
        }
    }
    private fun findBarcodeJob(value: String): Job {
        if (value != "error") {
            _uiState.value = _uiState.value.copy(
                barcodeStatus = EnteredBarcodeStatus.Searching
            )

            while (_afterTipingWaitingBarcodeTime < _maxAfterTipingWaitingBarcodeTime) {
                println("Searching in: " + (_maxAfterTipingWaitingBarcodeTime - _afterTipingWaitingBarcodeTime).toString() + "ms")
            }

            return viewModelScope.launch(Dispatchers.IO) {
                try {
                    _taquilla = getTaquillaByDescripcionCommon(value)
                } catch (e: Exception) {
                    _taquilla = MasterTaquilla()
                }
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(
                        barcodeStatus = if (_taquilla.Id > 0) { EnteredBarcodeStatus.Finded } else { EnteredBarcodeStatus.NotFinded },
                        barcode = value,
                        message = "Lectura correcta",
                        showMessage = true
                    )
                }
            }
        } else {
            return viewModelScope.launch {
                _uiState.value = _uiState.value.copy(
                    barcodeStatus = EnteredBarcodeStatus.Error,
                    barcode = "",
                    message = "Error al leer código, vuelva a intentarlo.",
                    showMessage = true
                )
            }
        }
    }
    private fun updateLecturaPrendasJob(): Job {
        return viewModelScope.launch{
            val toBeChecked = _listOfTags.count() - (_listOfPrendasEncontradas.count() + _listOfPrendasDesconocidas.count())
            _uiState.value = _uiState.value.copy(
                clientsList = _listOfPrendasEncontradasYUbicadas,
                unknownGarmentsList = _listOfPrendasDesconocidas,
                enableAssignmentButton = _listOfPrendasEncontradas.isNotEmpty() && toBeChecked == 0,
                total = _totalTags,
                unique = _listOfTags.count(),
                toBeChecked = toBeChecked,
                showMessage = false,
                message = ""
            )
        }
    }
    //end region

    //region Functions
    private fun cleanAll() {
        _uiState.value = _uiState.value.copy(
            clientsList = listOf(),
            unknownGarmentsList = listOf(),
            total = 0,
            unique = 0,
            toBeChecked = 0,
            showMessage = true,
            message = "Datos borrados satisfactoriamente.",
            barcode = ""
        )

        _listOfTags = listOf()
        _listOfPrendasEncontradas = listOf()
        _listOfPrendasDesconocidas = listOf()
        _listOfPrendasEncontradasYUbicadas = listOf()
        _totalTags = 0
        _barcode = mutableStateOf("")
    }
    private fun showLoadingWithMessage(show: Boolean, message: String = "Por favor, espere un momento...") {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = _uiState.value.copy(
                showLoadingMessage = show,
                loadingMessage = message
            )
        }
    }
    private fun showError(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = _uiState.value.copy(
                showMessage = true,
                message = text
            )
        }
    }
    private fun processNotVerifiedTags() {
        val notVerified = _listOfTags
            .minus(_listOfPrendasEncontradas.map { it.tag }.toSet())
            .minus(_listOfPrendasDesconocidas.map { it.tag }.toSet())

        notVerified.forEach {
            viewModelScope.launch(Dispatchers.Default) {
                verifyTag(it)
            }
        }
        _verifyTagTime = 0
    }
    private fun showAssignmentInitialDialog(showDialog: Boolean) {

        var text = "Se va a realizar la asignación de las prendas leídas a la taquilla: " +
                uiState.value.barcode +
                ". También se creará el movimiento de asignación correspondiente. "

        if (showDialog) {
            _uiState.value = _uiState.value.copy(
                dialogText = if (_listOfPrendasDesconocidas.isEmpty()) {
                    text
                } else {
                    text + "\n No se procesarán las " + _listOfPrendasDesconocidas.count() + " no reconocidas."
                },
                showAssignmentInitialDialog = true
            )
        } else {
            _uiState.value = _uiState.value.copy(
                dialogText = "",
                showAssignmentInitialDialog = false
            )
        }
    }
    private fun showAssignmentDoneDialog(showDialog: Boolean) {
        _uiState.value = _uiState.value.copy(
            dialogText = if (showDialog) { "Asignación de prendas completada." } else { "" },
            showAssignmentDoneDialog = showDialog
        )
    }
    //endregion
}