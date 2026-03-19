package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test

import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class TestConnectionRemote @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.testConnection()
    }
}