package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.model.MovMostrarAntenasPuesto
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAntenasPuestosCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(idPuesto: Int, useLocal: Boolean = false): List<MovMostrarAntenasPuesto> = withContext(defaultDispatcher) {
        val antenasPuestos = if (useLocal) {
            localRepository.getAntenasPuestos()
        } else {
            remoteRepository.getAntenasPuestos(idPuesto)
        }
        return@withContext antenasPuestos
    }
}
