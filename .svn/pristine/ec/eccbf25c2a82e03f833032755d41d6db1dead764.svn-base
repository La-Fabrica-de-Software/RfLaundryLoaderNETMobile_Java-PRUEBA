package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class GetClientesRemote @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(): List<MasterClientes> {
        return repository.getClientes()
    }
}