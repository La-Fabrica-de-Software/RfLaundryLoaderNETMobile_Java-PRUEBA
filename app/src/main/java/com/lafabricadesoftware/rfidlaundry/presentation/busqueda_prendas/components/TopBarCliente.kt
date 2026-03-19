package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBarCliente(
    scope: CoroutineScope, scaffoldState: ScaffoldState, busquedaPrendasViewModel: BusquedaPrendasViewModel
) {
    TopAppBar(
        elevation = 5.dp,
        title = {
            Text(stringResource(R.string.select_client))
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, stringResource(R.string.show_menu))
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
