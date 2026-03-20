package com.lafabricadesoftware.rfidlaundry.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda.BuscarPrendaViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.NavigationDrawerScreen
import com.lafabricadesoftware.rfidlaundry.presentation.splash.SplashScreen

@Composable
fun SplashNavigation(
    lecturaPrendasViewModel: LecturaPrendasViewModel,
    asignacionPrendasViewModel: AsignacionPrendasViewModel,
    buscarPrendaViewModel: BuscarPrendaViewModel
) {

    val navController = rememberNavController()
//    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screens.Main.route) {
            NavigationDrawerScreen(lecturaPrendasViewModel, asignacionPrendasViewModel, buscarPrendaViewModel)
        }
    }
}