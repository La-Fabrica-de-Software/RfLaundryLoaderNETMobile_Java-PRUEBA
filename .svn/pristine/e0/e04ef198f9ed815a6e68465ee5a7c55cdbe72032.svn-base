package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.subcliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import com.lafabricadesoftware.rfidlaundry.domain.util.OrderType
import com.lafabricadesoftware.rfidlaundry.domain.util.SubClienteOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSubClientesCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        idCliente: Int = 0,
        subClienteOrder: SubClienteOrder = SubClienteOrder.ByNombre(OrderType.Descending),
        useLocal: Boolean = false
    ): List<MasterSubClientes> = withContext(defaultDispatcher) {

        val subClientes = if (useLocal) {
            localRepository.getSubClientes(idCliente)
        } else {
            remoteRepository.getSubClientes(idCliente)
        }

        return@withContext when (subClienteOrder.orderType) {
            is OrderType.Ascending -> {
                when (subClienteOrder) {
                    is SubClienteOrder.ById -> subClientes.sortedByDescending { it.id }
                    is SubClienteOrder.ByNombre -> subClientes.sortedByDescending { it.Nombre }
                }
            }
            is OrderType.Descending -> {
                when (subClienteOrder) {
                    is SubClienteOrder.ById -> subClientes.sortedBy { it.id }
                    is SubClienteOrder.ByNombre -> subClientes.sortedBy { it.Nombre }
                }
            }
        }
    }
}
