package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.domain.util.EnteredBarcodeStatus
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.LoadingDialog
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components.TopBar
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components.BottomBar
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components.GrupoPrendaDesconocidaItem
import com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas.components.PrendaClienteItem
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.SuperDialog
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasEvent
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.components.MovementInformationDialog
import kotlinx.coroutines.CoroutineScope

@Composable
fun AsignarPrendasScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    asignacionPrendasViewModel: AsignacionPrendasViewModel
) {

    val barcodeState = asignacionPrendasViewModel.barcode.value

    val uiState = asignacionPrendasViewModel.uiState.collectAsStateWithLifecycle()

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    if (uiState.value.showMessage) {
        Toast.makeText(LocalContext.current, uiState.value.message, Toast.LENGTH_SHORT).show()
    }

    if (uiState.value.showLoadingMessage) {
        LoadingDialog(uiState.value.loadingMessage)
    }

    if (uiState.value.showAssignmentInitialDialog) {
        SuperDialog(
            title = "Asignación de prendas",
            text = uiState.value.dialogText,
            onDismiss = { asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentInitialDialog(false)) },
            onConfirm = {
                asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentInitialDialog(false))
                asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentDoneDialog(true))
            }
        )
    }

    if (uiState.value.showAssignmentDoneDialog) {
        SuperDialog(
            title = "Asignación terminada",
            text = uiState.value.dialogText,
            showCloseButtonOnly = true,
            onDismiss = { asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentDoneDialog(false)) },
            onConfirm = { asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.ShowAssignmentDoneDialog(false)) }
        )
    }

    Column(modifier = Modifier.background(Color(0xEEEEEEEE))) {
        TopBar(scope, scaffoldState, asignacionPrendasViewModel, uiState)
        Card(
            backgroundColor = colorResource(R.color.lfds_primary_900),
            shape = RectangleShape,
            elevation = AppBarDefaults.TopAppBarElevation - 1.dp
        ) {
            Barcode(asignacionPrendasViewModel, uiState.value.barcode, uiState.value.barcodeStatus)
        }
        Card(
            backgroundColor = colorResource(R.color.lfds_primary_900),
            shape = RectangleShape,
            elevation = AppBarDefaults.TopAppBarElevation - 2.dp
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
                        PrendaClienteItem(item = item)
                    }
                }
                if (uiState.value.unknownGarmentsList.isNotEmpty()) {
                    val theListOfUnknown = uiState.value.unknownGarmentsList.toList()
                    GrupoPrendaDesconocidaItem(prendas = theListOfUnknown)
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
        BottomBar(asignacionPrendasViewModel, uiState)
    }

}

@Composable
fun Barcode(asignacionPrendasViewModel: AsignacionPrendasViewModel, barcode: String, barcodeStatus: EnteredBarcodeStatus) {

    Column(modifier = Modifier.background(colorResource(R.color.lfds_primary_900))) {
        Card(
            modifier = Modifier.padding(0.dp),
            backgroundColor =
            if (barcode.isEmpty()) {
                colorResource(R.color.lfds_primary_900)
            } else {
                if (barcodeStatus == EnteredBarcodeStatus.Finded) {
                    Color(0xFF4CAF50)
                } else if (barcodeStatus == EnteredBarcodeStatus.Error || barcodeStatus == EnteredBarcodeStatus.NotFinded) {
                    Color.Red
                } else {
                    colorResource(R.color.lfds_primary_900)
                }
            },
            shape = RectangleShape,
//            elevation = AppBarDefaults.TopAppBarElevation - 1.dp
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp, 20.dp, 0.dp)
                .height(80.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,10.dp,0.dp,10.dp)
                    .weight(1f)
                    .height(60.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(0.dp, 0.dp, 5.dp, 0.dp),
                        value = barcode,
                        onValueChange = { asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.EnteredBarcode(it)) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            backgroundColor = Color(0x27000000),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Color.LightGray,
                            placeholderColor = Color.Gray
                        ),
                        textStyle = TextStyle(fontSize = 18.sp),
                        singleLine = true,
//                        placeholder = { Text(text = "Código de taquilla") },
                        trailingIcon = {
                            if (barcode != "") {
                                IconButton(
                                    onClick = { asignacionPrendasViewModel.onEvent(AsignacionPrendasEvent.EnteredBarcode("")) }
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "",
                                        tint = Color.LightGray,
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .size(24.dp)
                                    )
                                }
                            }
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .size(70.dp)
                        .padding(0.dp,10.dp,0.dp,0.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (barcode.isEmpty()) {
                        Icon(Icons.Filled.HelpOutline, "", tint = Color.White, modifier = Modifier.size(40.dp))
                    } else {
                        if (barcodeStatus == EnteredBarcodeStatus.Finded) {
                            Icon(Icons.Filled.CheckCircleOutline, "", tint = Color.White, modifier = Modifier.size(40.dp))
                        } else if (barcodeStatus == EnteredBarcodeStatus.Searching) {
                            Icon(Icons.Filled.Pending, "", tint = Color.White, modifier = Modifier.size(40.dp))
                        }  else if (barcodeStatus == EnteredBarcodeStatus.Error || barcodeStatus == EnteredBarcodeStatus.NotFinded) {
                            Icon(Icons.Filled.ErrorOutline, "", tint = Color.White, modifier = Modifier.size(40.dp))
                        } else {
                            Icon(Icons.Filled.HelpOutline, "", tint = Color.White, modifier = Modifier.size(40.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
}

