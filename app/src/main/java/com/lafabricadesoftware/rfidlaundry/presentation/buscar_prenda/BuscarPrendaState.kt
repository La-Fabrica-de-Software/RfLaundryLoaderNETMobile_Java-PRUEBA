package com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterModeloPrenda

data class BuscarPrendaState(
    val listClientes: List<MasterClientes> = emptyList(),
    val listModelos: List<MasterModeloPrenda> = emptyList(),
    val listTallas: List<String> = emptyList(),

    val selectedCliente: MasterClientes? = null,
    val selectedModelo: MasterModeloPrenda? = null,
    val selectedTalla: String = "",

    val isReading: Boolean = false,
    val prendaEncontrada: Boolean = false,
    val tagEncontrado: String = "",

    val showLoading: Boolean = false,
    val loadingMessage: String = "",
    val showMessage: Boolean = false,
    val message: String = "",
    val connectionStatus: Boolean = false
)
