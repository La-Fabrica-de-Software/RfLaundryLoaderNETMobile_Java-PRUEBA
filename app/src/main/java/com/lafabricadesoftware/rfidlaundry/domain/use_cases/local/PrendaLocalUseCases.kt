package com.lafabricadesoftware.rfidlaundry.domain.use_cases.local

import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda.AddPrenda
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda.DeletePrenda
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda.GetPrenda
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda.GetPrendasLocal

data class PrendaLocalUseCases(
    val getPrendasLocal: GetPrendasLocal,
//    val getPrenda: GetPrenda,
//    val addPrenda: AddPrenda,
//    val deletePrenda: DeletePrenda
)