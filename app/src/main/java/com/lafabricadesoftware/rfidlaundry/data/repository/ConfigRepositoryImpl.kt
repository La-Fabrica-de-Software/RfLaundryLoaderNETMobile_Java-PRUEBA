package com.lafabricadesoftware.rfidlaundry.data.repository

import com.lafabricadesoftware.rfidlaundry.data.data_source.config.ConfigDataCollector
import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.repository.ConfigRepository
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val file: ConfigDataCollector
): ConfigRepository {

    //region Configuracion
    override fun getConfiguracion(getDefault: Boolean): Configuracion {
        return file.getConfiguracion(getDefault)
    }
    override fun setConfiguracion(conf: Configuracion) {
        return file.setConfiguracion(conf)
    }
    //endregion
}