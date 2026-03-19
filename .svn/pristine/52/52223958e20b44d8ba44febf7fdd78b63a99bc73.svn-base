package com.lafabricadesoftware.rfidlaundry.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.vector.ImageVector
import com.lafabricadesoftware.rfidlaundry.R

const val NAVIGATION_DRAWER_ROUTE = "navigation_drawer"
const val BUSQUEDA_PRENDAS_ROUTE = "busqueda_prendas"

sealed class Screens(
    var route: String,
    var icon: ImageVector,
    var title: String
    ) {

    object Splash: Screens("splash_screen", Icons.Filled.Home, "Splash")

    object Main: Screens("main_screen", Icons.Filled.Home, R.string.app_name.toString())

    object LecturaPrendas: Screens("lectura_prendas_screen", Icons.Filled.List, "Lectura de prendas")

    object BusquedaPrendasCliente: Screens("busqueda_prendas_cliente_screen", Icons.Filled.ManageSearch, "Búsqueda de prendas")
    object BusquedaPrendasSubCliente: Screens("busqueda_prendas_subcliente_screen/{cliente}", Icons.Filled.Error, "Búsqueda de prendas") {
        fun passCliente(cliente: String): String { return "busqueda_prendas_subcliente_screen/$cliente" }
    }
    object BusquedaPrendasPrenda: Screens("busqueda_prendas_prenda_screen/{subCliente}", Icons.Filled.Error, "Búsqueda de prendas") {
        fun passSubCliente(subCliente: String): String { return "busqueda_prendas_prenda_screen/$subCliente" }
    }

    object AsignacionPrendas: Screens("asignacion_prendas_screen", Icons.Filled.SelectAll, "Asignación de prendas")

    object Configuracion: Screens("configuracion_screen", Icons.Filled.Settings, "Configuración")

    object Sincronizacion: Screens("sincronizacion_screen", Icons.Filled.Settings, "Sincronización de datos")

    object AcercaDe: Screens("acerca_de_screen", Icons.Filled.Info, "Acerca de")
}