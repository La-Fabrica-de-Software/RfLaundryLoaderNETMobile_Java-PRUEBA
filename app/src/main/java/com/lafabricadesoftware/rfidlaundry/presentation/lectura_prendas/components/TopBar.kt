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
        ActionItem("Buscar", icon = Icons.Filled.Search, action = { }, order = 1),
        ActionItem("Filtrar", icon = Icons.Filled.Filter, action = { }, order = 2),
        ActionItem("Ajustes", icon = Icons.Filled.Settings, action = { }, order = 3),
    )

    TopAppBar(
        elevation = AppBarDefaults.TopAppBarElevation,
        title = {
            Text("Lectura de prendas", color = Color.White)
        },
        backgroundColor = colorResource(R.color.lfds_primary_900),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "Show menu", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {
                lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.GroupData(!uiState.value.group))
            }) {
                if (uiState.value.group) {
                    Icon(Icons.Filled.Expand, "Desagrupar", tint = colorResource(R.color.white_66a))
                } else {
                    Icon(Icons.Filled.Compress, "Agrupar", tint = colorResource(R.color.white_66a))
                }

            }
            IconButton(onClick = {
                lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.CleanAllData)
            }) {
                Icon(Icons.Filled.Delete, "Borrar lectura", tint = colorResource(R.color.white_66a))
            }

            val (isExpanded, setExpanded) = remember { mutableStateOf(false) }
            OverflowMenuAction(isExpanded, setExpanded, options)

//            IconButton(onClick = {
//                mainViewModel.onEvent(MainUiEvent.CleanAllData)
//            }) {
//                Icon(Icons.Filled.MoreVert, null)
//            }
        }
    )
}








//    val actionItems = listOf(
//        ActionItem(
//            "Buscar",
//            Icons.Filled.Search,
//            action = { },
//            order = 1
//        ),
//        ActionItem(
//            "Filtrar",
//            Icons.Filled.Filter,
//            action = { },
//            order = 2
//        ),
//        ActionItem(
//            "Refrescar",
//            action = { },
//            order = 3
//        ),
//        ActionItem(
//            "Ajustes",
//            action = { },
//            order = 4
//        ),
//    )
//
//    ListTopAppBar(
//        openDrawer = { /* Abrimos un drawer */ },
//        actionItems = actionItems
//    )




//    TopAppBar(
//        title = { /*...*/ },
//        navigationIcon = {/*...*/},
//        actions = {
//            val (icons, options) = actionItems.partition { it.icon != null }
//            icons.forEach {
//                IconButton(onClick = it.action) {
//                    Icon(imageVector = it.icon!!, contentDescription = it.name)
//                }
//            }
//
//            val (isExpanded, setExpanded) = remember { mutableStateOf(false) }
//            OverflowMenuAction(isExpanded, setExpanded, options)
//        }
//    )