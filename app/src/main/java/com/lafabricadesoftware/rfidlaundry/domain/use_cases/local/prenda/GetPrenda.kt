package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository

class GetPrenda(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(id: Int): MasterPrendas {
        return repository.getPrendaById(id)
    }
}