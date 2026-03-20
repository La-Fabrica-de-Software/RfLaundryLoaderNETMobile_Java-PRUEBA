package com.lafabricadesoftware.rfidlaundry.domain.util

enum class AntennaModel(private val printableName: String? = null) {
    ChainwayC72("Chainway C72"),
    ZebraTC20("Zebra TC20"),
    ZebraTC21("Zebra TC21");

    fun getAntennaModelName(): String? {
        return printableName
    }
}