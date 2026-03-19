package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.LoadingDialog
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components.*
import com.lafabricadesoftware.rfidlaundry.presentation.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LecturaPrendasScreen(
    navController: NavHostController,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    lecturaPrendasViewModel: LecturaPrendasViewModel
) {

    val uiState = lecturaPrendasViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.showMessage) {
        Toast.makeText(LocalContext.current, uiState.value.message, Toast.LENGTH_SHORT).show()
    }

    if (uiState.value.showLoadingMessage) {
        LoadingDialog(uiState.value.loadingMessage)
    }

    if (uiState.value.showMovementsDialog) {
        MovementsDialog(
            uiState.value.movementsList,
            { lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.ShowMovementsDialog(false)) },
            { lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.ShowMovementInformationDialog(true, it)) }
        )
    }

    if (uiState.value.showMovementInformationDialog) {
        MovementInformationDialog(
            uiState.value.movementInformationMessage,
            uiState.value.selectedMovement,
            uiState.value.hideMovementDismissConfirmButtons,
            { lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.ShowMovementInformationDialog(false, 0)) },
            { lecturaPrendasViewModel.onEvent(LecturaPrendasEvent.GenerateMovement(
                if (uiState.value.hideMovementDismissConfirmButtons) {0} else {uiState.value.selectedMovement},
                uiState.value.selectedClientId,
                uiState.value.selectedSubClientId
            )) }
        )
    }

    Column(modifier = Modifier.background(Color(0xEEEEEEEE))) {
        TopBar(scope, scaffoldState, lecturaPrendasViewModel, uiState)
        Card(backgroundColor = colorResource(R.color.lfds_primary_900), shape = RectangleShape, elevation = AppBarDefaults.TopAppBarElevation - 1.dp
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Lecturas: ${uiState.value.total}", color = Color.LightGray)
                Text("Únicas: ${uiState.value.unique}", color = Color.LightGray)
                Text("Por verificar: ${uiState.value.toBeChecked}", color = Color.LightGray)
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())) {
            if (uiState.value.unknownGarmentsList.isNotEmpty() || uiState.value.clientsList.isNotEmpty()) {
                if (uiState.value.clientsList.isNotEmpty()) {
                    uiState.value.clientsList.forEach { item ->
                        PrendaClienteItem(item = item, uiState.value.group)
                    }
                }
                if (uiState.value.unknownGarmentsList.isNotEmpty()) {
                    val theListOfUnknown = uiState.value.unknownGarmentsList.toList()
                    GrupoPrendaDesconocidaItem(prendas = theListOfUnknown, uiState.value.group)
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.Transparent),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.value.connectionStatus) {
                        Text(text = "Esperando lectura")
                    } else {
                        Text(text = "No hay conexión", color = Color.Red)
                    }

                }
            }
        }
        BottomBar(lecturaPrendasViewModel, uiState)
    }

//    if (uiState.value.goToConfig) {
////        navController.popBackStack()
//        try {
//            navController.navigate(Screens.Configuracion.route)
//        } catch (e: Exception) {
//            println(e.message)
//        }
//    }
}