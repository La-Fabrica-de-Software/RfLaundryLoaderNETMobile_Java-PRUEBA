package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas

import com.lafabricadesoftware.rfidlaundry.domain.util.ClienteOrder
import com.lafabricadesoftware.rfidlaundry.domain.util.SubClienteOrder

sealed class BusquedaPrendasUiEvent {

    object GetClientes: BusquedaPrendasUiEvent()
    data class OrderClientes(val order: ClienteOrder): BusquedaPrendasUiEvent()
    data class SearchClientes(val text: String): BusquedaPrendasUiEvent()

    data class GetSubClientesById(val idCliente: Int): BusquedaPrendasUiEvent()
    data class OrderSubClientes(val order: SubClienteOrder): BusquedaPrendasUiEvent()

    data class GetPrendasByIdSub(val idSubCliente: Int): BusquedaPrendasUiEvent()

    data class ShowLoading(val show: Boolean, val text: String = "Por favor, espere..."): BusquedaPrendasUiEvent()
}