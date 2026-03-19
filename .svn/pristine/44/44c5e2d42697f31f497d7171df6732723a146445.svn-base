package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTaquilla
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTaquillasCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        useLocal: Boolean = false
    ): List<MasterTaquilla> = withContext(defaultDispatcher) {

        val taquillas = if (useLocal) {
            localRepository.getTaquillas()
        } else {
            remoteRepository.getTaquillas()
        }

        return@withContext taquillas
    }
}
