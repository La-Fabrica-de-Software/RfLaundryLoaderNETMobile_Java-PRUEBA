package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lafabricadesoftware.rfidlaundry.R
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.LoadingDialog
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasUiEvent
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente.components.SubClienteItem
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_subcliente.components.TopBarSubCliente

@Composable
fun BusquedaPrendasSubClienteScreen(
    navController: NavHostController,
    cliente: MasterClientes,
    busquedaPrendasViewModel: BusquedaPrendasViewModel = hiltViewModel()
) {

    val uiState = busquedaPrendasViewModel.uiState.collectAsStateWithLifecycle()
    busquedaPrendasViewModel.onEvent(BusquedaPrendasUiEvent.GetSubClientesById(cliente.id))

    Column(modifier = Modifier.background(Color(0xEEEEEEEE))
    ) {

        TopBarSubCliente(navController)

        if (uiState.value.showLoading) {
            LoadingDialog(uiState.value.loadingText)
        } else {
            if (uiState.value.listSubClientes.isNotEmpty()) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp, 4.dp, 4.dp, 0.dp)
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                ) {
                    uiState.value.listSubClientes.forEach {
                        SubClienteItem(navController, it)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.listing_subclients))
                }
            }
        }
    }
}
