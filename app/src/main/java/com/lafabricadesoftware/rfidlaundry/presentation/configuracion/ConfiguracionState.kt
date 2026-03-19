package com.lafabricadesoftware.rfidlaundry.presentation.configuracion

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion

data class ConfiguracionState(
    var configurationData: Configuracion = Configuracion(),
    val showMessage: Boolean = false,
    val message: String = ""
)