package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAntenasByPuestoCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(idPuesto: Int, useLocal: Boolean = false): List<MasterTipoAntena> = withContext(defaultDispatcher) {
        val antenasPuestos = if (useLocal) localRepository.getAntenasPuestos() else remoteRepository.getAntenasPuestos(idPuesto)

        val idsAntenas = antenasPuestos
            .let { if (idPuesto > 0) it.filter { it.idPuesto == idPuesto } else it }
            .map { it.idTipoAntena }
            .toSet()

        val antenas = (if (useLocal) localRepository.getAntenas() else remoteRepository.getAntenas())
            .filter { it.Id in idsAntenas }

        return@withContext antenas.sortedBy { it.descripcion }

    }
}
