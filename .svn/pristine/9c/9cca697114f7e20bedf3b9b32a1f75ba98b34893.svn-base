package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetPrendasLocal @Inject constructor(
    private val repository: LocalRepository
) {

    operator fun invoke(): Flow<List<MasterPrendas>> {
        return repository.getPrendasAsFlow()
    }
}
