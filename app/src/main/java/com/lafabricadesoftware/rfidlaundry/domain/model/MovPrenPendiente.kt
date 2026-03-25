package com.lafabricadesoftware.rfidlaundry.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovPrenPendiente(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var Id_Prenda: Int = 0,
    var Fecha: String = "",
    var id_Puesto: Int = 0,
    var id_TipoAntena: Int = 0,
    var id_Operario: String = "",
    var Obser: String = "",
    var idCli: Int = 0,
    var idSubCli: Int = 0,
    var idModeloPrenda: Int = 0,
    var talla: String = ""
)
