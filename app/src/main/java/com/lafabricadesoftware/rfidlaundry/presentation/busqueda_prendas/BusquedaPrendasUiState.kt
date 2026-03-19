package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.BusquedaPrendaPrenda
import com.lafabricadesoftware.rfidlaundry.domain.util.ClienteOrder
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import com.lafabricadesoftware.rfidlaundry.domain.util.SubClienteOrder

data class BusquedaPrendasUiState(
    val listClientes: List<MasterClientes> = emptyList(),
    val orderClientes: ClienteOrder = ClienteOrder.ByNombre(OrderType.Ascending),

    val listSubClientes: List<MasterSubClientes> = emptyList(),
    val orderSubClientes: SubClienteOrder = SubClienteOrder.ByNombre(OrderType.Ascending),

    val listPrendas: List<BusquedaPrendaPrenda> = emptyList(),

    val showMessage: Boolean = false,
    val message: String = "",
    val showLoading: Boolean = false,
    val loadingText: String = "Cargando..."
) {}
