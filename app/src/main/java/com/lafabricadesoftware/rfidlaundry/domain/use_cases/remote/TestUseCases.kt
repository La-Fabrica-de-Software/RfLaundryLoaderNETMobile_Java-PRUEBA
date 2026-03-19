package com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote

import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.InitConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionWithParametersRemote

data class TestUseCases(
    val initConnectionRemote: InitConnectionRemote,
    val testConnectionRemote: TestConnectionRemote,
    val testConnectionWithParametersRemote: TestConnectionWithParametersRemote
)