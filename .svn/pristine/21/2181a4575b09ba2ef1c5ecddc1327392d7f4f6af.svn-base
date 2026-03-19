package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lafabricadesoftware.rfidlaundry.presentation.common.components.LoadingDialog
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasUiEvent
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.BusquedaPrendasViewModel
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda.components.PrendaItem
import com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas.seleccion_prenda.components.TopBarPrenda

@Composable
fun BusquedaPrendasPrendaScreen (
    navController: NavHostController,
    subCliente: MasterSubClientes,
    busquedaPrendasViewModel: BusquedaPrendasViewModel = hiltViewModel()
) {

    val uiState = busquedaPrendasViewModel.uiState.collectAsStateWithLifecycle()
    busquedaPrendasViewModel.onEvent(BusquedaPrendasUiEvent.GetPrendasByIdSub(subCliente.id))

    Column(modifier = Modifier.background(Color(0xEEEEEEEE))
    ) {

        TopBarPrenda(navController)

        if (uiState.value.showLoading) {
            LoadingDialog(uiState.value.loadingText)
        } else {
            if (uiState.value.listPrendas.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxSize().padding(4.dp, 4.dp, 4.dp, 0.dp).weight(1f).verticalScroll(rememberScrollState())
                ) {
                    uiState.value.listPrendas.forEach {
                        PrendaItem(navController, it)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxSize().weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
//                    Button(onClick = { busquedaPrendasViewModel.onEvent(BusquedaPrendasUiEvent.GetClientes) }) {
//                        Icon(Icons.Filled.Refresh, "")
//                    }
                    Text(text = "Listando pendas...")
                }
            }
        }
    }
}