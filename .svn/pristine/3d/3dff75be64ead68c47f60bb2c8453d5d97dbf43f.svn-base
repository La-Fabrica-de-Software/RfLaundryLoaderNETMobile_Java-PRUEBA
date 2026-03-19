package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAntenasCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(useLocal: Boolean = false): List<MasterTipoAntena> = withContext(defaultDispatcher) {
        val antenas = if (useLocal) {
            localRepository.getAntenas()
        } else {
            remoteRepository.getAntenas()
        }
        return@withContext antenas
    }
}
