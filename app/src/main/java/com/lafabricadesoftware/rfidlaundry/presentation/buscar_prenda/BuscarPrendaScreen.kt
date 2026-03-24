package com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BuscarPrendaScreen(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    buscarPrendaViewModel: BuscarPrendaViewModel = hiltViewModel()
) {
    val uiState = buscarPrendaViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (uiState.value.showMessage) {
        Toast.makeText(context, uiState.value.message, Toast.LENGTH_SHORT).show()
    }

    if (uiState.value.showLoading) {
        LoadingDialog(uiState.value.loadingMessage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xEEEEEEEE))
    ) {
        TopAppBar(
            elevation = AppBarDefaults.TopAppBarElevation,
            title = {
                Text(stringResource(R.string.screen_buscar_prenda), color = Color.White)
            },
            backgroundColor = colorResource(R.color.lfds_primary_900),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { scaffoldState.drawerState.open() }
                }) {
                    Icon(Icons.Filled.Menu, stringResource(R.string.show_menu), tint = Color.White)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Cliente dropdown - only show when not pre-configured from settings
            if (!uiState.value.clientePreConfigurado) {
                FilterDropdown(
                    label = stringResource(R.string.buscar_prenda_label_cliente),
                    selectedText = uiState.value.selectedCliente?.Nombre
                        ?: stringResource(R.string.buscar_prenda_select_cliente),
                    items = uiState.value.listClientes.map { it.Nombre },
                    onItemSelected = { idx ->
                        buscarPrendaViewModel.onEvent(
                            BuscarPrendaEvent.SelectCliente(uiState.value.listClientes[idx])
                        )
                    },
                    enabled = true
                )
            }

            // Usuario (SubCliente) dropdown
            FilterDropdown(
                label = stringResource(R.string.buscar_prenda_label_usuario),
                selectedText = uiState.value.selectedSubCliente?.Nombre
                    ?: stringResource(R.string.buscar_prenda_select_usuario),
                items = uiState.value.listSubClientes.map { it.Nombre },
                onItemSelected = { idx ->
                    buscarPrendaViewModel.onEvent(
                        BuscarPrendaEvent.SelectSubCliente(uiState.value.listSubClientes[idx])
                    )
                },
                enabled = uiState.value.selectedCliente != null
            )

            // Modelo dropdown
            FilterDropdown(
                label = stringResource(R.string.buscar_prenda_label_modelo),
                selectedText = uiState.value.selectedModelo?.Descripcion
                    ?: stringResource(R.string.buscar_prenda_select_modelo),
                items = uiState.value.listModelos.map { it.Descripcion },
                onItemSelected = { idx ->
                    buscarPrendaViewModel.onEvent(
                        BuscarPrendaEvent.SelectModelo(uiState.value.listModelos[idx])
                    )
                },
                enabled = uiState.value.selectedSubCliente != null
            )

            // Talla dropdown
            FilterDropdown(
                label = stringResource(R.string.buscar_prenda_label_talla),
                selectedText = uiState.value.selectedTalla.ifEmpty {
                    stringResource(R.string.buscar_prenda_select_talla)
                },
                items = uiState.value.listTallas,
                onItemSelected = { idx ->
                    buscarPrendaViewModel.onEvent(
                        BuscarPrendaEvent.SelectTalla(uiState.value.listTallas[idx])
                    )
                },
                enabled = uiState.value.selectedModelo != null
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Read button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.value.isReading &&
                        uiState.value.selectedCliente != null &&
                        uiState.value.selectedSubCliente != null &&
                        uiState.value.selectedModelo != null &&
                        uiState.value.selectedTalla.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.lfds_primary_900),
                    contentColor = Color.White
                ),
                onClick = {
                    buscarPrendaViewModel.onEvent(BuscarPrendaEvent.ResetFound)
                    buscarPrendaViewModel.onEvent(BuscarPrendaEvent.StartReading)
                }
            ) {
                Text(
                    text = stringResource(R.string.buscar_prenda_btn_leer),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Stop button (visible while reading)
            if (uiState.value.isReading) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.lfds_primary_900),
                        contentColor = Color.White
                    ),
                    onClick = { buscarPrendaViewModel.onEvent(BuscarPrendaEvent.StopReading) }
                ) {
                    Text(
                        text = stringResource(R.string.buscar_prenda_btn_parar),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
                backgroundColor = when {
                    uiState.value.isReading -> Color(0xFFE3F2FD)
                    uiState.value.prendaEncontrada -> Color(0xFFE8F5E9)
                    else -> Color.White
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        uiState.value.isReading -> {
                            CircularProgressIndicator(
                                color = colorResource(R.color.lfds_primary_900)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.buscar_prenda_reading),
                                color = colorResource(R.color.lfds_primary_900),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        uiState.value.prendaEncontrada -> {
                            Text(
                                text = stringResource(R.string.buscar_prenda_found),
                                color = Color(0xFF2E7D32),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (uiState.value.tagEncontrado.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(
                                        R.string.buscar_prenda_tag,
                                        uiState.value.tagEncontrado
                                    ),
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                        else -> {
                            Text(
                                text = stringResource(R.string.buscar_prenda_waiting),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterDropdown(
    label: String,
    selectedText: String,
    items: List<String>,
    onItemSelected: (Int) -> Unit,
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (enabled) Color.DarkGray else Color.Gray,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded && enabled) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = Color.Gray,
                    disabledBorderColor = Color.LightGray
                )
            )
            if (enabled) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(enabled = items.isNotEmpty()) { expanded = true }
                )
                if (items.isNotEmpty()) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                onClick = {
                                    onItemSelected(index)
                                    expanded = false
                                }
                            ) {
                                Text(item)
                            }
                        }
                    }
                }
            }
        }
    }
}
