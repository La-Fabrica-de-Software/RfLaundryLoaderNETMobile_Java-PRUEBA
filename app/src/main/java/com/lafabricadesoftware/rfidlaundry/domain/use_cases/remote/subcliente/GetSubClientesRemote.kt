package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.subcliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterSubClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class GetSubClientesRemote @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(idCliente: Int): List<MasterSubClientes> {
        return repository.getSubClientes(idCliente)
    }
}