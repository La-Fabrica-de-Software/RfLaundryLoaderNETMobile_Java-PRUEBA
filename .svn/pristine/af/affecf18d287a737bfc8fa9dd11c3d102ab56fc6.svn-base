package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPrendaByTagCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke(
        tag: String,
        useLocal: Boolean = false
    ): MasterPrendas = withContext(defaultDispatcher) {

        val prenda = if (useLocal) {
            localRepository.getPrendaByTag(tag)
        } else {
            remoteRepository.getPrendaByTag(tag)
        }

        return@withContext prenda
    }
}
