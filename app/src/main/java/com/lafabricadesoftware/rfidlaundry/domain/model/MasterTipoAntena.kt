package com.lafabricadesoftware.rfidlaundry.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MasterTipoAntena(
    @PrimaryKey val Id: Int,
    val descripcion: String?,
    val MostrarEnPDA: Boolean,
    val Mostrar: Boolean,
    val NoBorrar: Boolean,
    val EsBaja: Boolean,
    val PedirUbicacion: Boolean
)