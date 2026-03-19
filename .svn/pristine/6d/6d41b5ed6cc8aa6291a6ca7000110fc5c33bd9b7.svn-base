package com.lafabricadesoftware.rfidlaundry.presentation.acerca_de.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lafabricadesoftware.rfidlaundry.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        elevation = 5.dp,
        title = {
            Text("Acerca de")
        },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "Show menu")
            }
        }, actions = {
            IconButton(onClick = {
                /* Do Something*/
            }) {
                Icon(Icons.Filled.Share, null)
            }
        }
    )
}