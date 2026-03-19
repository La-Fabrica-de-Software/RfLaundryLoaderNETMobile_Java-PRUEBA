package com.lafabricadesoftware.rfidlaundry.presentation.busqueda_prendas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.BusquedaPrendaPrenda
import com.lafabricadesoftware.rfidlaundry.domain.util.ClienteOrder
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.cliente.GetClientesCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.subcliente.GetSubClientesCommon
import com.lafabricadesoftware.rfidlaundry.domain.util.SubClienteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusquedaPrendasViewModel @Inject constructor(
    private val getClientesCommon: GetClientesCommon,
    private val getSubClientesCommon: GetSubClientesCommon,
    private val getPrendasCommon: GetPrendasCommon
) : ViewModel() {

    //region FIELDS

    private var _listClientes = listOf<MasterClientes>()
    private var _listSubClientes = listOf<MasterSubClientes>()
    private var _listPrendas = listOf<MasterPrendas>()

    //State
    private var _uiState = MutableStateFlow(BusquedaPrendasUiState())
    val uiState = _uiState.asStateFlow()

    //endregion

    init {
//        if (_listClientes.isEmpty()) {
//            onEvent(BusquedaPrendasUiEvent.GetClientes)
//        }
    }

    //region EVENTS
    fun onEvent(event: BusquedaPrendasUiEvent) {
        when(event) {
            is BusquedaPrendasUiEvent.GetClientes -> { loadClientes() }
            is BusquedaPrendasUiEvent.OrderClientes -> {
                if (_uiState.value.orderClientes::class == event.order::class &&
                    _uiState.value.orderClientes.orderType == event.order.orderType
                ) {
                    return
                }
                orderClientes(event.order)
            }
            is BusquedaPrendasUiEvent.SearchClientes -> {}
            is BusquedaPrendasUiEvent.GetSubClientesById -> { loadSubClientesByIdCliente(event.idCliente) }
            is BusquedaPrendasUiEvent.OrderSubClientes -> {
                if (_uiState.value.orderSubClientes::class == event.order::class &&
                    _uiState.value.orderSubClientes.orderType == event.order.orderType
                ) {
                    return
                }
                orderSubClientes(event.order)
            }
            is BusquedaPrendasUiEvent.GetPrendasByIdSub -> { loadPrendasByIdSubCliente(event.idSubCliente) }
            is BusquedaPrendasUiEvent.ShowLoading -> {
                _uiState.value = _uiState.value.copy(
                    showLoading = event.show,
                    loadingText = event.text
                )
            }
        }
    }
    //endregion

    //region FUNCTIONS
    private fun loadClientes() {
        viewModelScope.launch(Dispatchers.Main) {
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(true))
            _listClientes = getClientesCommon()
            _uiState.value = _uiState.value.copy(listClientes = _listClientes)
//            onEvent(BusquedaPrendasUiEvent.OrderClientes(ClienteOrder.ByNombre(OrderType.Ascending)))
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(false))
        }
    }
    private fun orderClientes(clienteOrder: ClienteOrder) {
        onEvent(BusquedaPrendasUiEvent.ShowLoading(true))
        _listClientes = when(clienteOrder.orderType) {
            is OrderType.Descending -> {
                when (clienteOrder) {
                    is ClienteOrder.ById -> _listClientes.sortedByDescending { it.id }
                    is ClienteOrder.ByNombre -> _listClientes.sortedByDescending { it.Nombre }
                }
            }
            is OrderType.Ascending -> {
                when (clienteOrder) {
                    is ClienteOrder.ById -> _listClientes.sortedBy { it.id }
                    is ClienteOrder.ByNombre -> _listClientes.sortedBy { it.Nombre }
                }
            }
        }
        _uiState.value = _uiState.value.copy(listClientes = _listClientes, listSubClientes = emptyList())
        onEvent(BusquedaPrendasUiEvent.ShowLoading(false))
    }

    private fun loadSubClientesByIdCliente(idCliente: Int) {
        viewModelScope.launch(Dispatchers.Main) {
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(true))
            cleanSubClientes()
            getSubClientesByIdCliente(idCliente)
            onEvent(BusquedaPrendasUiEvent.OrderSubClientes(SubClienteOrder.ByNombre(OrderType.Ascending)))
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(false))
        }
    }
    private suspend fun getSubClientesByIdCliente(idCliente: Int) {
        _listSubClientes = getSubClientesCommon(idCliente = idCliente)
        _uiState.value = _uiState.value.copy(listSubClientes = _listSubClientes)
    }
    private fun orderSubClientes(subClienteOrder: SubClienteOrder) {
        onEvent(BusquedaPrendasUiEvent.ShowLoading(true))
        _listSubClientes = when(subClienteOrder.orderType) {
            is OrderType.Descending -> {
                when (subClienteOrder) {
                    is SubClienteOrder.ById -> _listSubClientes.sortedByDescending { it.id }
                    is SubClienteOrder.ByNombre -> _listSubClientes.sortedByDescending { it.Nombre }
                }
            }
            is OrderType.Ascending -> {
                when (subClienteOrder) {
                    is SubClienteOrder.ById -> _listSubClientes.sortedBy { it.id }
                    is SubClienteOrder.ByNombre -> _listSubClientes.sortedBy { it.Nombre }
                }
            }
        }
        _uiState.value = _uiState.value.copy(listSubClientes = _listSubClientes)
        onEvent(BusquedaPrendasUiEvent.ShowLoading(false))
    }

    private fun loadPrendasByIdSubCliente(idSubCliente: Int) {
        viewModelScope.launch(Dispatchers.Main) {
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(true))
            cleanPrendas()
            getPrendasByIdSubCliente(idSubCliente)
//            onEvent(BusquedaPrendasUiEvent.ShowLoading(false))
        }
    }
    private suspend fun getPrendasByIdSubCliente(idSubCliente: Int) {
        _listPrendas = getPrendasCommon(idSubCliente = idSubCliente)
        var listPrendasTemp = emptyList<BusquedaPrendaPrenda>()
        for (_listPrenda in _listPrendas) {
            val prendaTemp = BusquedaPrendaPrenda(_listPrenda,false)
            listPrendasTemp = listPrendasTemp + prendaTemp
        }
        _uiState.value = _uiState.value.copy(listPrendas = listPrendasTemp)
    }

    private fun cleanSubClientes() {
        _uiState.value = _uiState.value.copy(listSubClientes = emptyList())
    }
    private fun cleanPrendas() {
        _uiState.value = _uiState.value.copy(listPrendas = emptyList())
    }
    //endregion
}