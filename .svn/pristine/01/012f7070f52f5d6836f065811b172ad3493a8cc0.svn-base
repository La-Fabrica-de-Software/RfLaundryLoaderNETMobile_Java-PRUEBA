package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPrendasCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        idSubCliente: Int = 0,
        useLocal: Boolean = false
    ): List<MasterPrendas> = withContext(defaultDispatcher) {

        val prendas = if (useLocal) {
            localRepository.getPrendas(idSubCliente)
        } else {
            remoteRepository.getPrendas(idSubCliente)
        }

        return@withContext prendas
    }
}
