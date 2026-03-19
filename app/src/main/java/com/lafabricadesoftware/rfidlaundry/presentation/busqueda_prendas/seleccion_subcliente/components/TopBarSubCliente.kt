package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TopBarSubCliente(
    navController: NavHostController,
) {
    TopAppBar(
        elevation = 5.dp,
        title = {
            Text("Seleccione un subcliente")
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Filled.Search, null)
            }
        }
    )
}