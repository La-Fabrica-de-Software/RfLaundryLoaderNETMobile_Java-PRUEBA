package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common

import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren.SetMovPrenCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaClienteSubClienteByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla.GetTaquillaByDescripcionCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.taquilla.GetTaquillasCommon

class TaquillaCommonUseCases(
    val getTaquillasCommon: GetTaquillasCommon,
    val getTaquillaByDescripcionCommon: GetTaquillaByDescripcionCommon
)