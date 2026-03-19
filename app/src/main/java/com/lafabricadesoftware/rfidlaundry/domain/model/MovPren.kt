package com.lafabricadesoftware.rfidlaundry.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovPren(
    @PrimaryKey var Id: Int = 0,
    var id_mov: Int = 0,
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


/*
        [PrimaryKey]
        public int Id { get; set; }
        public int? id_mov { get; set; }
        public int? Id_Prenda { get; set; }
        public DateTime? Fecha { get; set; }
        public DateTime? Hora { get; set; }
        public int? id_Puesto { get; set; }
        public int? id_TipoAntena { get; set; }
        public string id_Operario { get; set; }
        public string Obser { get; set; }
        public string NumeroLote { get; set; }
        public int? idCli { get; set; }
        public int? idSubCli { get; set; }
        public string AlbaranLavander { get; set; }
        public int idModeloPrenda { get; set; }
        public string talla { get; set; }
 */