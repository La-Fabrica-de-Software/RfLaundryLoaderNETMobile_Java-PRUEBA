package com.lafabricadesoftware.rfidlaundry.presentation.navigation

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasClienteScreen
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda.BusquedaPrendasPrendaScreen
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente.BusquedaPrendasSubClienteScreen
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.busquedaPrendasNavigation(
    navController: NavHostController,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    navigation(
        startDestination = Screens.BusquedaPrendasCliente.route,
        route = BUSQUEDA_PRENDAS_ROUTE
    ) {

        composable(Screens.BusquedaPrendasCliente.route) {
            BusquedaPrendasClienteScreen(navController, scope, scaffoldState)
        }

        composable(
            Screens.BusquedaPrendasSubCliente.route, listOf(
                navArgument("cliente") { type = NavType.StringType }
            )
        ) {
            it.arguments?.getString("cliente").let { json ->
                val cliente = Gson().fromJson(json, MasterClientes::class.java)
                requireNotNull(cliente)
                BusquedaPrendasSubClienteScreen(navController, cliente)
            }
        }

        composable(
            Screens.BusquedaPrendasPrenda.route, listOf(
                navArgument("subCliente") { type = NavType.StringType }
            )
        ) {
            it.arguments?.getString("subCliente").let { json ->
                val subCliente = Gson().fromJson(json, MasterSubClientes::class.java)
                requireNotNull(subCliente)
                BusquedaPrendasPrendaScreen(navController, subCliente)
            }
        }
    }
}