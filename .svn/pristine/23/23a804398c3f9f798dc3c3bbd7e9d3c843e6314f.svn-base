package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.model.MovPren
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLastMovPrenByIdPrendaCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(idPrenda: Int, useLocal: Boolean = false): MovPren = withContext(defaultDispatcher) {
        val movPren = if (useLocal) {
            localRepository.getLastMovPrenByIdPrenda(idPrenda)
        } else {
            remoteRepository.getLastMovPrenByIdPrenda(idPrenda)
        }
        return@withContext movPren
    }
}
