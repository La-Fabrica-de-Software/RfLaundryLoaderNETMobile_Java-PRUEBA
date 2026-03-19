package com.lafabricadesoftware.rfidlaundry.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovMostrarAntenasPuesto(
    @PrimaryKey val id: Int = 0,
    val idPuesto: Int,
    val idTipoAntena: Int
)