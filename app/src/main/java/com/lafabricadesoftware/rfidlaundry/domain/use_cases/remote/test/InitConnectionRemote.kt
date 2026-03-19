package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class InitConnectionRemote @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(config: Configuracion): Boolean {
        return repository.initConnection(config)
    }
}