package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.sub_cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import com.lafabricadesoftware.rfidlaundry.domain.util.SubClienteOrder
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetSubClientesLocal @Inject constructor(
    private val repository: LocalRepository
) {

    operator fun invoke(
        subClienteOrder: SubClienteOrder = SubClienteOrder.ByNombre(OrderType.Descending)
    ): Flow<List<MasterSubClientes>> {

        return repository.getSubClientesAsFlow().map { subClientes ->
            when(subClienteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(subClienteOrder) {
                        is SubClienteOrder.ById -> subClientes.sortedByDescending { it.id }
                        is SubClienteOrder.ByNombre -> subClientes.sortedByDescending { it.Nombre }
                    }
                }
                is OrderType.Descending -> {
                    when(subClienteOrder) {
                        is SubClienteOrder.ById -> subClientes.sortedBy { it.id }
                        is SubClienteOrder.ByNombre -> subClientes.sortedBy { it.Nombre }
                    }
                }
            }
        }
    }
}