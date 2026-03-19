package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository

class DeletePrenda(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(prenda: MasterPrendas) {
        repository.deletePrenda(prenda)
    }
}