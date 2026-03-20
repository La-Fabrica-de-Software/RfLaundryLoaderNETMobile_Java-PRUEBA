package com.lafabricadesoftware.rfidlaundry.presentation.navigation

import androidx.activity.viewModels
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lafabricadesoftware.rfidlaundry.presentation.acerca_de.AcercaDeScreen
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignarPrendasScreen
import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.ConfiguracionScreen
import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.ConfiguracionViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda.BuscarPrendaScreen
import com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda.BuscarPrendaViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasScreen
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel1

@Composable
fun NavigationDrawerNavigation(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    lecturaPrendasViewModel: LecturaPrendasViewModel,
    asignacionPrendasViewModel: AsignacionPrendasViewModel,
    buscarPrendaViewModel: BuscarPrendaViewModel
){
    NavHost(
        navController,
        startDestination = Screens.LecturaPrendas.route
    ){

        composable(Screens.LecturaPrendas.route){
            LecturaPrendasScreen(navController, scope, scaffoldState, lecturaPrendasViewModel)
        }

        composable(Screens.BuscarPrenda.route){
            BuscarPrendaScreen(scope, scaffoldState, buscarPrendaViewModel)
        }

        busquedaPrendasNavigation(navController, scope, scaffoldState)

        composable(Screens.AsignacionPrendas.route){
            AsignarPrendasScreen(scope, scaffoldState, asignacionPrendasViewModel)
        }

        composable(Screens.Configuracion.route){
            ConfiguracionScreen(scope, scaffoldState)
        }

        composable(Screens.AcercaDe.route){
            AcercaDeScreen(scope, scaffoldState)
        }
    }
}
