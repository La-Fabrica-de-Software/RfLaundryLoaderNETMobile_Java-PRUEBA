package com.lafabricadesoftware.rfidlaundry.presentation.configuracion.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.ConfiguracionEvent
import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.ConfiguracionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBarConfiguracion(
    scope: CoroutineScope, scaffoldState: ScaffoldState, viewModel: ConfiguracionViewModel
) {
    TopAppBar(
        elevation = 5.dp,
        title = {
            Text(stringResource(R.string.screen_configuracion))
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
            IconButton(onClick = { viewModel.onEvent(ConfiguracionEvent.OnLoadDefault) }) {
                Icon(Icons.Filled.Restore, null)
            }
            IconButton(onClick = { viewModel.onEvent(ConfiguracionEvent.OnSave) }) {
                Icon(Icons.Filled.Save, null)
            }
        }
    )
}
