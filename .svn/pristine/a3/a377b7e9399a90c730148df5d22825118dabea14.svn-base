package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TopBarPrenda(
    navController: NavHostController,
) {
    TopAppBar(
        elevation = 5.dp,
        title = {
            Text("Prendas")
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        }
    )
}