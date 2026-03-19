package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.util.ClienteOrder
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetClientesLocal @Inject constructor(
    private val repository: LocalRepository
) {

    operator fun invoke(
        clienteOrder: ClienteOrder = ClienteOrder.ByNombre(OrderType.Descending)
    ): Flow<List<MasterClientes>> {

        return repository.getClientesAsFlow().map { clientes ->
            when(clienteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(clienteOrder) {
                        is ClienteOrder.ById -> clientes.sortedByDescending { it.id }
                        is ClienteOrder.ByNombre -> clientes.sortedByDescending { it.Nombre }
                    }
                }
                is OrderType.Descending -> {
                    when(clienteOrder) {
                        is ClienteOrder.ById -> clientes.sortedBy { it.id }
                        is ClienteOrder.ByNombre -> clientes.sortedBy { it.Nombre }
                    }
                }
            }
        }
    }
}