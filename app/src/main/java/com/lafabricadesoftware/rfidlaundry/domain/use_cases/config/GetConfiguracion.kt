package com.lafabricadesoftware.rfidlaundry.domain.use_cases.config

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.repository.ConfigRepository
import javax.inject.Inject

class GetConfiguracion @Inject constructor(
    private val repository: ConfigRepository
) {

    operator fun invoke(getDefault: Boolean = false): Configuracion {
        return repository.getConfiguracion(getDefault)
    }
}