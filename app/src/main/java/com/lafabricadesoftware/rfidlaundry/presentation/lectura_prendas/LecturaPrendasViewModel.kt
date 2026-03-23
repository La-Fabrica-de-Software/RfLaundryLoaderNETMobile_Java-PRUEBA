package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.*
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena.GetAntenasByPuestoCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena.GetAntenasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren.SetMovPrenCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaClienteSubClienteByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.InitConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionRemote
import com.lafabricadesoftware.rfidlaundry.util.DateTimeUtils
import com.rscja.deviceapi.RFIDWithUHFUART
import com.rscja.deviceapi.entity.UHFTAGInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LecturaPrendasViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localRepository: LocalRepository,
    private val getConfiguracion: GetConfiguracion,
    private val getPrendaClienteSubClienteByTagCommon: GetPrendaClienteSubClienteByTagCommon,
    private val getAntenasCommon: GetAntenasByPuestoCommon,
    private val initConnectionRemote: InitConnectionRemote,
    private val testConnectionRemote: TestConnectionRemote,
    private val setMovPrenCommon: SetMovPrenCommon
//    private val getLastMovPrenByIdPrendaCommon: GetLastMovPrenByIdPrendaCommon
) : ViewModel() {

    //region Variables

    //Reader
    private var _reader: RFIDWithUHFUART? = null
    private var _inventoryFlag = 0
    private var _triggerPressed = false

    //Tags
    private var _listOfTags = listOf<String>()
//    private var _listOfTagsControl = listOf<TagControl>()
    private var _totalTags: Int = 0

    //Prendas
    private var _listOfPrendasEncontradas = listOf<Prenda>()
    private var _listOfPrendasDesconocidas = listOf<Prenda>()
    private var _listOfPrendasEncontradasYUbicadas = listOf<PrendaCliente>()

    //Antenas
    private var _listOfAntenas = listOf<MasterTipoAntena>()

    //State
    private val _uiState = MutableStateFlow(LecturaPrendasState())
    val uiState = _uiState.asStateFlow()

    //Jobs
    private var lecturaPrendasJob: Job? = null
    private var testConnectionJob: Job? = null
    private var getAntenasJob: Job? = null
    private var filterPrendas: Job? = null
    private var generateMovementsJob: Job? = null

    //Time
    private var verifyTime = 0L

    //endregion

    fun listOflistOfPrendasEncontradas(): List<Prenda>{
        return listOf(
            Prenda(idPrenda = 1, nombreModeloPrenda = "nombreModeloPrenda", nombrePrenda = "Garment1", talla = "Size1", tag = "Tag 1", isExiste = true, isBorrado = false, isVerificado = false, idCliente = 1, isClienteBorrado = false, idModeloPrenda = 1),
            Prenda(idPrenda = 2, nombreModeloPrenda = "nombreModeloPrenda", nombrePrenda = "Garment2", talla = "Size2", tag = "Tag 2", isExiste = true, isBorrado = true, isVerificado = false, idCliente = 1, isClienteBorrado = false, idModeloPrenda = 1),
            Prenda(idPrenda = 3, nombreModeloPrenda = "nombreModeloPrenda", nombrePrenda = "Garment1", talla = "Size1", tag = "Tag 3", isExiste = true, isBorrado = false, isVerificado = false, idCliente = 1, isClienteBorrado = false, idModeloPrenda = 1)
        )
    }

    fun listOflistOfPrendasDesconocidas(): List<Prenda>{
        return listOf(
            Prenda(idPrenda = 4, nombreModeloPrenda = "nombreModeloPrenda", nombrePrenda = "Garment1", talla = "Size1", tag = "Tag 4", isExiste = true, isBorrado = false, isVerificado = false, idCliente = 1, isClienteBorrado = false, idModeloPrenda = 1)

        )
    }

    fun listOfClients(): List<PrendaCliente>{
        return listOf(
            PrendaCliente(idCliente = 1, nombreCliente = "Client 1",
            listOf(
                PrendaSubCliente(idSubCliente = 2, nombreSubCliente = "Sub Cliente 1", listadoPrenda =
                listOflistOfPrendasEncontradas(), vestuario = "Vestuario"
                )
            ), isBorrado = true
            )
        )
    }
    init {
        //_listOfPrendasDesconocidas=    listOflistOfPrendasDesconocidas()
        //_listOfTags = listOf("TAG 1","Tag 2","Tag 3")
        //_listOfPrendasEncontradas = listOflistOfPrendasEncontradas()
        //_listOfPrendasEncontradasYUbicadas = listOfClients()

        //GlobalScope.launch(Dispatchers.Main){
        //    localRepository.getPrendaByTag("A")
        //}

        initReader(1)

//        var configExist = getConfiguracion().server != ""
//        if (!configExist) {
//            _uiState.value = _uiState.value.copy(
//                goToConfig = true
//            )
//        }
        println("---------------------------------------------- antennaId: jai -- ---")
        onEvent(LecturaPrendasEvent.TestConnection)

        Timer().scheduleAtFixedRate( object : TimerTask() {
            override fun run() {

                onEvent(LecturaPrendasEvent.UpdateData)
                if (verifyTime > 10000 &&
                    _listOfTags.isNotEmpty() &&
                    (_listOfTags.count() - (_listOfPrendasEncontradas.count() + _listOfPrendasDesconocidas.count())) > 0) {

                    processNotVerifiedTags()
                }
                verifyTime += 500
            }
        }, 0, 500)
    }

    //region Reader
    private fun initReader(inventoryFlag: Int) {
        stopInventory()
        freeReader()
        _inventoryFlag = inventoryFlag

        try {
            _reader = RFIDWithUHFUART.getInstance()
            println("+++++ initReader - Reader instance created +++++")
        } catch (e: Exception) {
            println("----- initReader - Reader instance exception: ${e.message}")
            return
        }
        if (_reader != null ) {
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
    fun freeReader() {
        try {
            _reader?.free()
            println("+++++ freeReader Reader free OK +++++")
        } catch (e: Exception) {
            println("----- freeReader Reader free exception: ${e.message}")
        }
    }
    //endregion

    //region Inventory
    fun startInventory() {
        verifyTime = 0
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
//            _listOfTagsControl = _listOfTagsControl + TagControl(tag, TagEstadoType.NotVerified)
            viewModelScope.launch(Dispatchers.Default) {
                verifyTag(tag)
            }
        }
        onEvent(LecturaPrendasEvent.UpdateData)
    }
    private suspend fun verifyTag(tag: String) {
        try {
//            _listOfTagsControl.first { it.tag == tag }.state = TagEstadoType.Verifing
            val prenda = getPrendaClienteSubClienteByTagCommon(tag, config = getConfiguracion())
            viewModelScope.launch(Dispatchers.Main) {
                placeTag(prenda)
                withContext(Dispatchers.Main) {
                    onEvent(LecturaPrendasEvent.UpdateData)
                }
            }
//            _listOfTagsControl.first { it.tag == tag }.state = TagEstadoType.Verified
        } catch (e: Exception) {
            showError(e.message.toString())
//            _listOfTagsControl.first { it.tag == tag }.state = TagEstadoType.NotVerified
//            throw e
        }
    }
    private fun placeTag(prenda: LecturaPrendaClienteSubCliente) {
        verifyTime = 0
        if (prenda.idPrenda > 0) {
            val newClientePrenda = PrendaCliente(prenda.idCliente, prenda.nombreCliente, mutableListOf(), isBorrado = prenda.clienteBorrado)
            val newSubClientePrenda = PrendaSubCliente(prenda.idSubCliente, prenda.nombreSubCliente, mutableListOf(), isBorrado = prenda.subClienteBorrado, vestuario = prenda.vestuarioSubCliente)
            val newPrenda = Prenda(prenda.idPrenda, prenda.nombrePrenda, prenda.talla, prenda.tag, true, prenda.isBorrado, true, newClientePrenda.idCliente, prenda.clienteBorrado, newSubClientePrenda.idSubCliente, prenda.subClienteBorrado, prenda.idModeloPrenda, prenda.nombreModeloPrenda, prenda.lastMovFecha, prenda.lastMov)

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

    //region Events
    fun onEvent(event: LecturaPrendasEvent) {
        when(event) {
            is LecturaPrendasEvent.TestConnection -> {
                testConnectionJob?.cancel()
                testConnectionJob = testConnectionJob()
            }
            is LecturaPrendasEvent.UpdateData -> {
                lecturaPrendasJob?.cancel()
                lecturaPrendasJob = updateLecturaPrendasJob()
            }
            is LecturaPrendasEvent.CleanAllData -> {
                cleanAll()
            }
            is LecturaPrendasEvent.GroupData -> {
                _uiState.value = _uiState.value.copy(
                    group = !_uiState.value.group,
                    showMessage = true,
                    message = if (_uiState.value.group) { "Prendas agrupadas" } else { "Prendas desagrupadas" }
                )
            }
            is LecturaPrendasEvent.ShowMessage -> {
            }
            is LecturaPrendasEvent.ShowLoadingWithMessage -> {
            }
            is LecturaPrendasEvent.ShowMovementsDialog -> {if (event.showDialog) {
                    getAntenasJob?.cancel()
                    getAntenasJob = getAntenasJob(getConfiguracion.invoke().workstationId)
                } else {
                    _uiState.value = _uiState.value.copy(
                        showMovementsDialog = false
                    )
                }
            }
            is LecturaPrendasEvent.ShowMovementInformationDialog -> {
                if (event.showDialog) {
                    filterPrendas?.cancel()
                    filterPrendas = filterPrendasJob(event.antennaId)
                } else {
                    _uiState.value = _uiState.value.copy(
                        showMovementInformationDialog = false
                    )
                }
            }
            is LecturaPrendasEvent.GenerateMovement -> {
                _uiState.value = _uiState.value.copy(
                    showMovementInformationDialog = false,
                    showMovementsDialog = false
                )
                if (event.antennaId != 0) {
                    generateMovementsJob?.cancel()
                    generateMovementsJob = generateMovementsJob(event.antennaId, event.clientId, event.subClientId)
                }
            }
            else -> {}
        }
    }
    //endregion

    //region Jobs
    private fun testConnectionJob(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            initConnectionRemote(getConfiguracion())
            val testConnResult = testConnectionRemote()
            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(
                    connectionStatus = testConnResult,
                    showLoadingMessage = false,
                    showMessage = false
                )
            }
        }
    }
    private fun updateLecturaPrendasJob(): Job {
        return viewModelScope.launch{
            //val toBeChecked = _listOfTags.count() - (_listOfPrendasEncontradas.count() + _listOfPrendasDesconocidas.count())
            val toBeChecked = 0
            _uiState.value = _uiState.value.copy(
                clientsList = _listOfPrendasEncontradasYUbicadas,
                unknownGarmentsList = _listOfPrendasDesconocidas,
                enableMovementsButton = _listOfPrendasEncontradas.isNotEmpty() && toBeChecked == 0,
                total = _totalTags,
                unique = _listOfTags.count(),
                toBeChecked = toBeChecked,
                showMessage = false,
                message = ""
            )
        }
    }
    private fun getAntenasJob(idPuesto: Int): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            showLoadingWithMessage(true, "Cargando antenas. Espere un momento.")
            _listOfAntenas = getAntenasCommon(idPuesto).toList()
            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(
                    showMovementsDialog = true,
                    movementsList = _listOfAntenas,
                    showLoadingMessage = false,
                    showMessage = false
                )
            }
        }
    }
    private fun filterPrendasJob(antennaId: Int): Job {
        return viewModelScope.launch(Dispatchers.Default) {
            showLoadingWithMessage(true, "Filtrando prendas. Espere un momento.")
            //val prendasForMovement = _listOfPrendasEncontradas.filter {
            //    !it.isClienteBorrado && !it.isSubClienteBorrado && !it.isBorrado &&
            //    DateTimeUtils().getStringToLocalDate(it.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate())
            //}.toList()
            val prendasForMovement = _listOfPrendasEncontradas.filter {
                !it.isClienteBorrado && !it.isSubClienteBorrado && !it.isBorrado &&
                (DateTimeUtils().getStringToLocalDate(it.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate()) ||
                    it.lastMov != antennaId)
            }.toList()

            val movementsForAllFindedPrendas = _listOfPrendasEncontradas.count() == prendasForMovement.count()
            var text = ""
            var hideDismissConfirmButtons = false

            if (movementsForAllFindedPrendas) {
                text = String.format("Se generarán movimientos para las %d prendas encontradas.\n", prendasForMovement.count())
            } else {
                val countWithClienteBorrado = _listOfPrendasEncontradas.count { it.isClienteBorrado || it.isBorrado || it.isSubClienteBorrado }
                val countWithSubClienteBorrado = _listOfPrendasEncontradas.count { !it.isClienteBorrado && it.isSubClienteBorrado }
                val countWithPrendaBorrada = _listOfPrendasEncontradas.count { !it.isClienteBorrado && !it.isSubClienteBorrado && it.isBorrado }
                //val countWithMovementsToday = _listOfPrendasEncontradas.count { !it.isClienteBorrado && !it.isSubClienteBorrado && !it.isBorrado &&
                //                              !DateTimeUtils().getStringToLocalDate(it.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate()) }
                val countWithMovementsToday = _listOfPrendasEncontradas.count { !it.isClienteBorrado && !it.isSubClienteBorrado && !it.isBorrado &&
                        (!DateTimeUtils().getStringToLocalDate(it.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate()) ||
                                it.lastMov == antennaId) }
                val unidentifiedGarmentCount = _listOfPrendasDesconocidas.count{!it.isExiste}
                if (prendasForMovement.isEmpty()) {
                    text += String.format("No se generarán movimientos para las %d prendas identificadas.\nA continuación se detalla el motivo:\n\n",
                        _listOfPrendasEncontradas.count()
                    )
                } else {
                    text += String.format("Se generarán movimientos para %d prendas de un total de %d identificadas.\nA continuación se detalla el motivo y la cantidad de las no procesadas:\n\n",
                        prendasForMovement.count(),
                        _listOfPrendasEncontradas.count()
                    )
                }

                //if (countWithClienteBorrado > 0) {
                //    text += String.format("  - %d prendas no identificadas \n", countWithClienteBorrado)
                //}
                if (unidentifiedGarmentCount>0){
                    text += String.format("  - %d prendas no identificadas \n", unidentifiedGarmentCount)
                }
                if (countWithSubClienteBorrado > 0) {
                    text += String.format("  - Usuario borrado: %d\n", countWithSubClienteBorrado)
                }
                if (countWithPrendaBorrada > 0) {
                    text += String.format("  - Prenda borrada: %d\n", countWithPrendaBorrada)
                }
                if (countWithMovementsToday > 0) {
                    //text += String.format("  - Con movimientos hoy: %d\n", countWithMovementsToday)
                    text += String.format("  - %d prendas con último movimiento igual que el movimiento a insertar\n", countWithMovementsToday)
                }
            }

            if (prendasForMovement.isNotEmpty()) {
                text += String.format("\n¿Desea continuar?")
            }

            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(
                    selectedMovement = antennaId,
                    showLoadingMessage = false,
                    showMovementInformationDialog = true,
                    movementInformationMessage = text,
                    hideMovementDismissConfirmButtons = prendasForMovement.isEmpty()
                )
            }
        }
    }
    private fun generateMovementsJob(antennaId: Int, clientId: Int, subClientId: Int): Job {
        return viewModelScope.launch(Dispatchers.Default) {
            //showLoadingWithMessage(true, "Generando movimientos. Espere un momento.")

            //val prendasForMovement = _listOfPrendasEncontradas.filter { p ->
            //    !p.isClienteBorrado && !p.isSubClienteBorrado && (!p.isBorrado && DateTimeUtils().getStringToLocalDate(p.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate()) )
            //}.toList()
            val prendasForMovement = _listOfPrendasEncontradas.filter { p ->
                !p.isClienteBorrado && !p.isSubClienteBorrado && !p.isBorrado &&
                (DateTimeUtils().getStringToLocalDate(p.dateLastMovPrenda).isBefore(LocalDate.now().atStartOfDay().toLocalDate()) ||
                p.lastMov != antennaId)
            }.toList()
            println("---------------------------------------------- antennaId: $antennaId -- ---")
            prendasForMovement.forEach {
                println("---------------------------------------------- lastMov: ${it.lastMov} -- ---")
            }
            var result: Boolean
            val count = prendasForMovement.count()
            if (count > 0) {
                //generateMovements(antennaId, clientId, subClientId, prendasForMovement)
                //result = true
                generateMovementsInThread(antennaId, clientId, subClientId, prendasForMovement)
            } else {
                result = false
            }

            //withContext(Dispatchers.Main) {
            //    if (result) {
            //        _uiState.value = _uiState.value.copy(
            //            showMovementsDialog = false,
            //            showLoadingMessage = false,
            //            showMessage = true,
            //            message = "Se generaron movimientos para ${prendasForMovement.count()} prendas de las ${_listOfPrendasEncontradas.count()} encontradas."
            //        )
            //    } else {
            //        _uiState.value = _uiState.value.copy(
            //            showMovementsDialog = false,
            //            showLoadingMessage = false,
            //            showMessage = true,
            //            message = if (count > 0) {
            //                "Ocurrió un error al generar los movimientos."
            //            } else {
            //                "No se encontraron prendas válidas para generar movimientos."
            //            }
            //        )
            //    }
            //}
            cleanAll()
        }
    }
    //endregion

    //region Functions
    private fun cleanAll() {
        _uiState.value = _uiState.value.copy(
            clientsList = listOf(),
            unknownGarmentsList = listOf(),
            movementsList = listOf(),
            enableMovementsButton = false,
            total = 0,
            unique = 0,
            toBeChecked = 0,
            showMessage = true,
            message = "Datos borrados satisfactoriamente."
        )

        _listOfTags = listOf()
//        _listOfTagsControl = listOf()
        _listOfPrendasEncontradas = listOf()
        _listOfPrendasDesconocidas = listOf()
        _listOfPrendasEncontradasYUbicadas = listOf()
        _totalTags = 0
//        _totalTimeString = "0.0"
//        _message = ""
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
    private fun generateMovements(antennaId: Int, clientId: Int, subClientId: Int, prendasForMovement: List<Prenda>) {
        setMovPrenCommon(antennaId, clientId, subClientId, prendasForMovement)
    }

    private fun showThreadMessage(result: Boolean, itemCount: Int): Job{
        return viewModelScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main) {
                if (result) {
                    _uiState.value = _uiState.value.copy(
                        showMovementsDialog = false,
                        showLoadingMessage = false,
                        showMessage = true,
                        message = if (itemCount == 1) {
                            "Se ha generado ${itemCount} movimiento exitosamente."
                        } else {
                            "Se han generado ${itemCount} movimientos exitosamente."
                        }
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        showMovementsDialog = false,
                        showLoadingMessage = false,
                        showMessage = true,
                        message = if (itemCount > 0) {
                            "Ocurrió un error al generar los movimientos."
                        } else {
                            "No se encontraron prendas válidas para generar movimientos."
                        }
                    )
                }
            }
        }
    }
    private fun generateMovementsInThread(antennaId: Int, clientId: Int, subClientId: Int, prendasForMovement: List<Prenda>) {
        var looper=Looper.getMainLooper()
        GlobalScope.launch {
            setMovPrenCommon(antennaId, clientId, subClientId, prendasForMovement)
            Handler(looper).post{
                showThreadMessage(true, prendasForMovement.count())
            }
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
        verifyTime = 0

//        println("---------------------------------------------- Tiempo: $verifyTime -- ---")
//        println("---------------------------------------------- Encontradas: ${_listOfPrendasEncontradas.count()} -- ---")
//        println("---------------------------------------------- Desconocidas: ${_listOfPrendasDesconocidas.count()} -- ---")
//        println()
    }
    //endregion

}



//    private fun ifPrendaDoesntHaveMovementTodayByPrenda(idPrenda: Prenda): Boolean {
//        var toReturn = false
////        viewModelScope.launch(Dispatchers.IO) {
////            val lastMov = try {
////                getLastMovPrenByIdPrendaCommon(idPrenda)
////            } catch (e: Exception) {
////                MovPren()
////            }
////
//////            val qwerty1 = DateTime().getStringToDate(lastMov.Fecha).format(DateTimeFormatter.ofPattern(ONLY_DATE))
//////            val qwerty2 = LocalDate.now().atStartOfDay().format(DateTimeFormatter.ofPattern(ONLY_DATE))
//////            println("+ + + Fecha del último movimiento: $qwerty1 y fecha actual: $qwerty2")
////
////            withContext(Dispatchers.Main) {
////                if (lastMov.Id == 0 || (lastMov.Id > 0 && DateTime().getStringToDate(lastMov.Fecha).isBefore(LocalDate.now().atStartOfDay().toLocalDate()))) {
////                    println("+ + + Fecha de movimiento antiguo: ${DateTime().getStringToDate(lastMov.Fecha)}")
////                    toReturn = true
////                } else {
////                    println("+ + + Tiene movimientos hoy la prenda: $idPrenda")
////                }
////            }
////        }
////        println("+ + + $toReturn + + +")
//        return toReturn
//    }

