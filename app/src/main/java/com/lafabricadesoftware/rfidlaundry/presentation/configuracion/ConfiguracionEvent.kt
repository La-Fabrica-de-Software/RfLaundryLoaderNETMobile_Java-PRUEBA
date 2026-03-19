package com.lafabricadesoftware.rfidlaundry.presentation.configuracion

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion

sealed class ConfiguracionEvent {
    object OnInit: ConfiguracionEvent()

    data class EnteredServer(val value: String): ConfiguracionEvent()
    data class EnteredPort(val value: String): ConfiguracionEvent()
    data class EnteredDatabase(val value: String): ConfiguracionEvent()
    data class EnteredUsername(val value: String): ConfiguracionEvent()
    data class EnteredPassword(val value: String): ConfiguracionEvent()

    data class EnteredClientId(val value: String): ConfiguracionEvent()
    data class EnteredWorkstationId(val value: String): ConfiguracionEvent()

    //    data class EnteredAntennaPower(val value: Float): ConfiguracionEvent()

    data class SelectedOnlineOnly(val value: Boolean): ConfiguracionEvent()
    data class SelectedReadBarcode(val value: Boolean): ConfiguracionEvent()

    object OnLoadDefault: ConfiguracionEvent()
    object OnSave: ConfiguracionEvent()
    object OnMessageShown: ConfiguracionEvent()
    object OnConfigurationLoaded: ConfiguracionEvent()
    object OnTestNoParam: ConfiguracionEvent()
    //data class OnTest(val configuracion: Configuracion): ConfiguracionEvent()
    object OnTest: ConfiguracionEvent()
    object OnClean: ConfiguracionEvent()

//    data class EnteredServer(val value: String): ConfiguracionEvent()
//    data class EnteredPort(val value: String): ConfiguracionEvent()
//    data class EnteredDatabase(val value: String): ConfiguracionEvent()
//    data class EnteredUsername(val value: String): ConfiguracionEvent()
//    data class EnteredPassword(val value: String): ConfiguracionEvent()
//    object SaveConfig: ConfiguracionEvent()
}
