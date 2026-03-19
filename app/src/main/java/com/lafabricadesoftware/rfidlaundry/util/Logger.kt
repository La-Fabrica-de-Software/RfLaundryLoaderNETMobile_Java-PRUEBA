package com.lafabricadesoftware.rfidlaundry.util

class Logger() {


    fun writeInLog(messageType: MessageType, text: String) {
        val fileName = "LOG_${DateTimeUtils().getActualFormattedDate()}.log"
//        if (File.getFilesDir())

//        if (File.)
    }
}


enum class MessageType {
    Error("ERRO"),
    Warning("WARN"),
    Information("INFO");

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