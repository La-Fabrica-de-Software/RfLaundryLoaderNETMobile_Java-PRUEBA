package com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterModeloPrenda
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.InitConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionRemote
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
import javax.inject.Inject

@HiltViewModel
class BuscarPrendaViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localRepository: LocalRepository,
    private val getConfiguracion: GetConfiguracion,
    private val initConnectionRemote: InitConnectionRemote,
    private val testConnectionRemote: TestConnectionRemote
) : ViewModel() {

    //region Variables

    private var _reader: RFIDWithUHFUART? = null
    private var _triggerPressed = false

    private val _uiState = MutableStateFlow(BuscarPrendaState())
    val uiState = _uiState.asStateFlow()

    private var testConnectionJob: Job? = null

    //endregion

    init {
        initReader()
        testConnectionJob = testConnectionJob()
        onEvent(BuscarPrendaEvent.LoadClientes)
    }

    //region Reader

    private fun initReader() {
        stopInventory()
        freeReader()
        try {
            _reader = RFIDWithUHFUART.getInstance()
            println("+++++ BuscarPrenda initReader - Reader instance created +++++")
        } catch (e: Exception) {
            println("----- BuscarPrenda initReader - Reader instance exception: ${e.message}")
            return
        }
        if (_reader != null) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    try {
                        _reader?.setEPCMode()
                        _reader?.init(context)
                        println("+++++ BuscarPrenda initReader - Reader init OK +++++")
                    } catch (e: Exception) {
                        println("----- BuscarPrenda initReader - Reader init exception: ${e.message}")
                    }
                }
            }
        }
    }

    fun reinitReader() {
        initReader()
    }

    fun freeReader() {
        try {
            _reader?.free()
            println("+++++ BuscarPrenda freeReader OK +++++")
        } catch (e: Exception) {
            println("----- BuscarPrenda freeReader exception: ${e.message}")
        }
    }

    //endregion

    //region Inventory

    fun startInventory() {
        if (_uiState.value.selectedCliente == null ||
            _uiState.value.selectedModelo == null ||
            _uiState.value.selectedTalla.isEmpty()
        ) {
            _uiState.value = _uiState.value.copy(
                showMessage = true,
                message = "Seleccione usuario, modelo y talla antes de leer"
            )
            return
        }

        _triggerPressed = true
        _uiState.value = _uiState.value.copy(
            isReading = true,
            prendaEncontrada = false,
            tagEncontrado = "",
            showMessage = false
        )

        if (_reader?.startInventoryTag() == true) {
            println("----- BuscarPrenda Start inventory")
            viewModelScope.launch(Dispatchers.IO) {
                getContinuousEpc()
            }
        } else {
            stopInventory()
        }
    }

    fun stopInventory() {
        _triggerPressed = false
        try {
            _reader?.stopInventory()
            println("+++++ BuscarPrenda stopInventory OK")
        } catch (e: Exception) {
            println("----- BuscarPrenda stopInventory Exception: ${e.message}")
        }
        _uiState.value = _uiState.value.copy(isReading = false)
    }

    private fun getContinuousEpc() {
        var res: UHFTAGInfo?
        while (_triggerPressed) {
            res = _reader?.readTagFromBuffer()
            if (res != null) {
                processEpc(res.epc)
            }
        }
    }

    private fun processEpc(epc: String) {
        val tag = if (epc.length > 16) epc.takeLast(16) else epc
        viewModelScope.launch(Dispatchers.Default) {
            checkTagMatchesFilter(tag)
        }
    }

    private suspend fun checkTagMatchesFilter(tag: String) {
        try {
            val prenda = localRepository.getPrendaByTag(tag)
            if (prenda.Id > 0 && matchesFilter(prenda)) {
                withContext(Dispatchers.Main) {
                    stopInventory()
                    playBeep()
                    _uiState.value = _uiState.value.copy(
                        isReading = false,
                        prendaEncontrada = true,
                        tagEncontrado = tag
                    )
                }
            }
        } catch (e: Exception) {
            println("----- BuscarPrenda checkTagMatchesFilter exception: ${e.message}")
        }
    }

    private fun matchesFilter(prenda: MasterPrendas): Boolean {
        val selectedCliente = _uiState.value.selectedCliente ?: return false
        val selectedModelo = _uiState.value.selectedModelo ?: return false
        val selectedTalla = _uiState.value.selectedTalla.ifEmpty { return false }

        return prenda.id_Cliente == selectedCliente.id &&
                prenda.id_ModeloPrenda == selectedModelo.id &&
                prenda.Talla == selectedTalla &&
                !prenda.borrado
    }

    private fun playBeep() {
        try {
            val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
            Handler(Looper.getMainLooper()).postDelayed({
                toneGenerator.release()
            }, 500)
        } catch (e: Exception) {
            println("----- BuscarPrenda playBeep exception: ${e.message}")
        }
    }

    //endregion

    //region Events

    fun onEvent(event: BuscarPrendaEvent) {
        when (event) {
            is BuscarPrendaEvent.LoadClientes -> loadClientes()
            is BuscarPrendaEvent.SelectCliente -> {
                _uiState.value = _uiState.value.copy(
                    selectedCliente = event.cliente,
                    selectedModelo = null,
                    selectedTalla = "",
                    listModelos = emptyList(),
                    listTallas = emptyList(),
                    prendaEncontrada = false
                )
                if (event.cliente != null) {
                    loadModelosForCliente(event.cliente)
                }
            }
            is BuscarPrendaEvent.SelectModelo -> {
                _uiState.value = _uiState.value.copy(
                    selectedModelo = event.modelo,
                    selectedTalla = "",
                    listTallas = emptyList(),
                    prendaEncontrada = false
                )
                val cliente = _uiState.value.selectedCliente
                if (event.modelo != null && cliente != null) {
                    loadTallasForClienteAndModelo(cliente, event.modelo)
                }
            }
            is BuscarPrendaEvent.SelectTalla -> {
                _uiState.value = _uiState.value.copy(
                    selectedTalla = event.talla,
                    prendaEncontrada = false
                )
            }
            is BuscarPrendaEvent.StartReading -> startInventory()
            is BuscarPrendaEvent.StopReading -> stopInventory()
            is BuscarPrendaEvent.ResetFound -> {
                _uiState.value = _uiState.value.copy(
                    prendaEncontrada = false,
                    tagEncontrado = ""
                )
            }
        }
    }

    //endregion

    //region Load data

    private fun loadClientes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val clientes = localRepository.getClientes()
                    .filter { !it.Borrado }
                    .sortedBy { it.Nombre }
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(listClientes = clientes)
                }
            } catch (e: Exception) {
                println("----- BuscarPrenda loadClientes exception: ${e.message}")
            }
        }
    }

    private fun loadModelosForCliente(cliente: MasterClientes) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prendas = localRepository.getPrendas(0)
                    .filter { it.id_Cliente == cliente.id && !it.borrado }
                val modeloIds = prendas.map { it.id_ModeloPrenda }.distinct()
                val modelos = modeloIds.mapNotNull { id ->
                    try {
                        localRepository.getModeloPrenda(id)
                    } catch (e: Exception) {
                        null
                    }
                }.sortedBy { it.Descripcion }
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(listModelos = modelos)
                }
            } catch (e: Exception) {
                println("----- BuscarPrenda loadModelosForCliente exception: ${e.message}")
            }
        }
    }

    private fun loadTallasForClienteAndModelo(cliente: MasterClientes, modelo: MasterModeloPrenda) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tallas = localRepository.getPrendas(0)
                    .filter { it.id_Cliente == cliente.id && it.id_ModeloPrenda == modelo.id && !it.borrado }
                    .map { it.Talla }
                    .filter { it.isNotEmpty() }
                    .distinct()
                    .sorted()
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(listTallas = tallas)
                }
            } catch (e: Exception) {
                println("----- BuscarPrenda loadTallasForClienteAndModelo exception: ${e.message}")
            }
        }
    }

    private fun testConnectionJob(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            try {
                initConnectionRemote(getConfiguracion())
                val result = testConnectionRemote()
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(connectionStatus = result)
                }
            } catch (e: Exception) {
                println("----- BuscarPrenda testConnectionJob exception: ${e.message}")
            }
        }
    }

    //endregion
}
