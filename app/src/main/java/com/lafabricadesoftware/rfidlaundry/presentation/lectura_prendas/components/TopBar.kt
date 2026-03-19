package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components

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
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.OverflowMenuAction
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.model.ActionItem
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasEvent
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasState
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    lecturaPrendasViewModel: LecturaPrendasViewModel,
    uiState: State<LecturaPrendasState>,
//    actionItems: List<ActionItem>
) {
    val options = listOf(
        ActionItem(stringResource(R.string.action_search), icon = Icons.Filled.Search, action = { }, order = 1),
        ActionItem(stringResource(R.string.action_filter), icon = Icons.Filled.Filter, action = { }, order = 2),
        ActionItem(stringResource(R.string.action_settings), icon = Icons.Filled.Settings, action = { }, order = 3),
    )

    TopAppBar(
        elevation = AppBarDefaults.TopAppBarElevation,
        title = {
            Text(stringResource(R.string.screen_lectura_prendas), color = Color.White)
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
                lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.GroupData(!uiState.value.group))
            }) {
                if (uiState.value.group) {
                    Icon(Icons.Filled.Expand, stringResource(R.string.action_ungroup), tint = colorResource(R.color.white_66a))
                } else {
                    Icon(Icons.Filled.Compress, stringResource(R.string.action_group), tint = colorResource(R.color.white_66a))
                }

            }
            IconButton(onClick = {
                lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.CleanAllData)
            }) {
                Icon(Icons.Filled.Delete, stringResource(R.string.action_delete_reading), tint = colorResource(R.color.white_66a))
            }

            val (isExpanded, setExpanded) = remember { mutableStateOf(false) }
            OverflowMenuAction(isExpanded, setExpanded, options)
        }
    )
}
