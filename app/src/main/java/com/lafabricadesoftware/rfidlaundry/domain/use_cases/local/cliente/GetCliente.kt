package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository

class GetCliente(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(id: Int): MasterClientes {
        return repository.getClienteById(id)
    }
}