package com.lafabricadesoftware.rfidlaundry.domain.repository

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion

interface ConfigRepository {

    fun getConfiguracion(getDefault: Boolean = false): Configuracion
    fun setConfiguracion(config: Configuracion)
}