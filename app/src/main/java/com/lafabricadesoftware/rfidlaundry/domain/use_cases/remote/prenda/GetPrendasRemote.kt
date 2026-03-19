package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class GetPrendasRemote @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(
        idSubCliente: Int
    ): List<MasterPrendas> {
        return repository.getPrendas(idSubCliente)
    }
}