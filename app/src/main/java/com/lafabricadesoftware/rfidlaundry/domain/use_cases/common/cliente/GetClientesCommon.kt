package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import com.lafabricadesoftware.rfidlaundry.domain.util.ClienteOrder
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetClientesCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        clienteOrder: ClienteOrder = ClienteOrder.ByNombre(OrderType.Descending),
        forceLocal: Boolean = false
    ): List<MasterClientes> = withContext(defaultDispatcher) {

        val clientes = if (forceLocal) {
            localRepository.getClientes()
        } else {
            remoteRepository.getClientes()
        }

        return@withContext when (clienteOrder.orderType) {
            is OrderType.Ascending -> {
                when (clienteOrder) {
                    is ClienteOrder.ById -> clientes.sortedByDescending { it.id }
                    is ClienteOrder.ByNombre -> clientes.sortedByDescending { it.Nombre }
                }
            }
            is OrderType.Descending -> {
                when (clienteOrder) {
                    is ClienteOrder.ById -> clientes.sortedBy { it.id }
                    is ClienteOrder.ByNombre -> clientes.sortedBy { it.Nombre }
                }
            }
        }
    }
}
