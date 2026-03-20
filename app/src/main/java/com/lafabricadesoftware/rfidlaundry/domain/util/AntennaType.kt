package com.lafabricadesoftware.rfidlaundry.domain.util

enum class AntennaType(private val realId: Int? = null, private val printableName: String? = null) {
    AltaPrenda(0, "Alta"),
    Entrada(1, "Entrada"),
    Salida(2, "Salida"),
    Costura(3, "Alta"),
    CambioTitularidad(6, "Cambio titularidad"),
    CambioEtiqueta(7,"Cambio etiqueta"),
    SalidaAlmacen(9, "Salida almacén"),
    Baja(10, "Baja");

    fun getAntennaTypeNumberAsNumber(): Int? {
        return realId
    }
    fun getAntennaTypeNumberAsString(): String {
        return realId.toString()
    }
    fun getAntennaTypeName(): String? {
        return printableName
    }
}

/*
* PrendaRecuperada = 11,
MarcadoPrenda = 12,
Reetiquetado = 13,
CambioTag = 40,
MaquinaSucio = 50,
PrendaDispensada = 51,
PrendaRetiradaMaquinaSucio = 52,
PrendaCargadaDispensadora = 53,
EntradaLenceria = 54,
PrendaDispensadaLenceria = 55,
Retorno = 102*/