package com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.NavigationDrawerNavigation
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens
import com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.components.DrawerBottom
import com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.components.DrawerHeader
import com.lafabricadesoftware.rfidlaundry.presentation.navigation_drawer.components.DrawerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavigationDrawerScreen(
    lecturaPrendasViewModel: LecturaPrendasViewModel,
    asignacionPrendasViewModel: AsignacionPrendasViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerContentScreen(scope, scaffoldState, navController)
        }
    ) {
        NavigationDrawerNavigation(scope, scaffoldState, navController, lecturaPrendasViewModel, asignacionPrendasViewModel)
    }
}

@Composable
fun NavigationDrawerContentScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {

//    val activity = LocalContext.current as Activity

    Column(modifier = Modifier
        .background(color = Color.White)
    ) {

        DrawerHeader()
        Spacer(modifier = Modifier.fillMaxWidth().height(5.dp)
        )

        println("- - - - - - - - - - -")
        println("- - - - MENU  - - - -")
        println("- - - - - - - - - - -")

        val menuItems = listOf(
            Screens.LecturaPrendas,
            //Screens.BusquedaPrendasCliente,
            //Screens.AsignacionPrendas,
            Screens.Configuracion,
            Screens.AcercaDe
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        menuItems.forEach { item ->
            DrawerItem(
                item = item,
                selected = currentRoute == item.route,
                onItemClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route){ saveState = false }
                        }
                        launchSingleTop = false
                        restoreState = false
                    }
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DrawerBottom()
    }
}