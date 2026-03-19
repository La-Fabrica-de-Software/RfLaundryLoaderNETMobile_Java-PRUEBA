package com.lafabricadesoftware.rfidlaundry.data.data_source.config

import android.content.Context
import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.util.Config
import javax.inject.Inject

class ConfigDataCollector @Inject constructor(
    private val context: Context
) {

    fun getConfiguracion(getDefault: Boolean = false): Configuracion {
        val config = Config(context)
        return config.getConfiguracion(getDefault)
    }

    fun setConfiguracion(conf: Configuracion) {
        val config = Config(context)
        config.setConfiguracion(conf)
//        HikariCPDataSource.init(conf, false)
    }
}