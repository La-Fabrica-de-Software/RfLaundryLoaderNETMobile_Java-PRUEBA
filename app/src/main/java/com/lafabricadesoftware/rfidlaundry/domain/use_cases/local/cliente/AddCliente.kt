package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente

import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes

class AddCliente(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(cliente: MasterClientes) {
        repository.insertCliente(cliente)
    }
}