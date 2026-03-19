package com.lafabricadesoftware.rfidlaundry.domain.util

enum class AntennaModel {
    ChainwayC72("Chainway C72"),
    ZebraTC20("Zebra TC20"),
    ZebraTC21("Zebra TC21");

    private var printableName: String? = null

    constructor()
    constructor(
        printableName: String
    ) {
        this.printableName = printableName
    }

    fun getAntennaModelName(): String? {
        return printableName
    }
}