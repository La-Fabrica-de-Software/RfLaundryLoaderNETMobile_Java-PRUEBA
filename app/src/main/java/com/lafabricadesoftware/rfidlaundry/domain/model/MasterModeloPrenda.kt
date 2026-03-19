package com.lafabricadesoftware.rfidlaundry.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MasterModeloPrenda(
    @PrimaryKey var id: Int = 0,
    var Descripcion: String = ""
)