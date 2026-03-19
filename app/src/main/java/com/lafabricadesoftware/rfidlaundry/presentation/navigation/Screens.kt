package com.lafabricadesoftware.rfidlaundry.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.vector.ImageVector
import com.lafabricadesoftware.rfidlaundry.R

const val NAVIGATION_DRAWER_ROUTE = "navigation_drawer"
const val BUSQUEDA_PRENDAS_ROUTE = "busqueda_prendas"

sealed class Screens(
    var route: String,
    var icon: ImageVector,
    @StringRes var title: Int
    ) {

    object Splash: Screens("splash_screen", Icons.Filled.Home, R.string.screen_splash)

    object Main: Screens("main_screen", Icons.Filled.Home, R.string.app_name)

    object LecturaPrendas: Screens("lectura_prendas_screen", Icons.Filled.List, R.string.screen_lectura_prendas)

    object BusquedaPrendasCliente: Screens("busqueda_prendas_cliente_screen", Icons.Filled.ManageSearch, R.string.screen_busqueda_prendas)
    object BusquedaPrendasSubCliente: Screens("busqueda_prendas_subcliente_screen/{cliente}", Icons.Filled.Error, R.string.screen_busqueda_prendas) {
        fun passCliente(cliente: String): String { return "busqueda_prendas_subcliente_screen/$cliente" }
    }
    object BusquedaPrendasPrenda: Screens("busqueda_prendas_prenda_screen/{subCliente}", Icons.Filled.Error, R.string.screen_busqueda_prendas) {
        fun passSubCliente(subCliente: String): String { return "busqueda_prendas_prenda_screen/$subCliente" }
    }

    object AsignacionPrendas: Screens("asignacion_prendas_screen", Icons.Filled.SelectAll, R.string.screen_asignacion_prendas)

    object Configuracion: Screens("configuracion_screen", Icons.Filled.Settings, R.string.screen_configuracion)

    object Sincronizacion: Screens("sincronizacion_screen", Icons.Filled.Settings, R.string.screen_sincronizacion)

    object AcercaDe: Screens("acerca_de_screen", Icons.Filled.Info, R.string.screen_acerca_de)
}
