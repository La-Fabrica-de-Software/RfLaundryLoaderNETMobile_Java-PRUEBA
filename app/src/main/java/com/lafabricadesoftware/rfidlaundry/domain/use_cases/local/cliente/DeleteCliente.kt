package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository

class DeleteCliente(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(cliente: MasterClientes) {
        repository.deleteCliente(cliente)
    }
}