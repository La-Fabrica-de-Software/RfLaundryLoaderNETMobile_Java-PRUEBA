package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda

import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas

class AddPrenda(
    private val repository: LocalRepository
) {

    suspend operator fun invoke(prenda: MasterPrendas) {
        repository.insertPrenda(prenda)
    }
}