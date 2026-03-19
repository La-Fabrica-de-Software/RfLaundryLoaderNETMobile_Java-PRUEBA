package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTaquilla
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTaquillaByDescripcionCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        descripcion: String,
        useLocal: Boolean = false
    ): MasterTaquilla = withContext(defaultDispatcher) {

        val taquilla = if (useLocal) {
            localRepository.getTaquillaByDescripcion(descripcion)
        } else {
            remoteRepository.getTaquillaByDescripcion(descripcion)
        }

        return@withContext taquilla
    }
}
