package com.lafabricadesoftware.rfidlaundry.domain.use_cases.config

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.repository.ConfigRepository
import javax.inject.Inject

class SetConfiguracion @Inject constructor(
    private val repository: ConfigRepository
) {

    operator fun invoke(conf: Configuracion) {
        repository.setConfiguracion(conf)
    }
}