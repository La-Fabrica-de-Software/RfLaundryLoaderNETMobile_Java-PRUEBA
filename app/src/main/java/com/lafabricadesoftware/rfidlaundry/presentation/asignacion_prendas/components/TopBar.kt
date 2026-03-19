package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasEvent
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasState
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.AsignacionPrendasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    asignacionPrendasViewModel: AsignacionPrendasViewModel,
    uiState: State<AsignacionPrendasState>,
//    actionItems: List<ActionItem>
) {

    TopAppBar(
        elevation = AppBarDefaults.TopAppBarElevation,
        title = {
            Text(stringResource(R.string.screen_asignacion_prendas), color = Color.White)
        },
        backgroundColor = colorResource(R.color.lfds_primary_900),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, stringResource(R.string.show_menu), tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {
                asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.CleanAllData)
            }) {
                Icon(Icons.Filled.Delete, stringResource(R.string.action_delete_reading), tint = colorResource(R.color.white_66a))
            }
        }
    )
}
