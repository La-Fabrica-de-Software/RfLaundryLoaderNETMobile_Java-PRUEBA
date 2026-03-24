package com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterModeloPrenda
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes

data class BuscarPrendaState(
    val listClientes: List<MasterClientes> = emptyList(),
    val listSubClientes: List<MasterSubClientes> = emptyList(),
    val listModelos: List<MasterModeloPrenda> = emptyList(),
    val listTallas: List<String> = emptyList(),

    val selectedCliente: MasterClientes? = null,
    val selectedSubCliente: MasterSubClientes? = null,
    val selectedModelo: MasterModeloPrenda? = null,
    val selectedTalla: String = "",

    val isReading: Boolean = false,
    val prendaEncontrada: Boolean = false,
    val tagEncontrado: String = "",

    val clientePreConfigurado: Boolean = false,

    val showLoading: Boolean = false,
    val loadingMessage: String = "",
    val showMessage: Boolean = false,
    val message: String = "",
    val connectionStatus: Boolean = false
)
