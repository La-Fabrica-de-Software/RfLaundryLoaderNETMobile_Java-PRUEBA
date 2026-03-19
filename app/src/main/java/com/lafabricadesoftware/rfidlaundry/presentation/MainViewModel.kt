package com.lafabricadesoftware.rfidlaundry.presentation

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.lifecycle.ViewModel
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConfiguracion: GetConfiguracion
) : ViewModel() {

    private val driver = "net.sourceforge.jtds.jdbc.Driver"

    init {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Class.forName(driver)
    }
}