package com.lafabricadesoftware.rfidlaundry.util

class Logger() {


    fun writeInLog(messageType: MessageType, text: String) {
        val fileName = "LOG_${DateTimeUtils().getActualFormattedDate()}.log"
//        if (File.getFilesDir())

//        if (File.)
    }
}


enum class MessageType(private val printableName: String? = null) {
    Error("ERRO"),
    Warning("WARN"),
    Information("INFO");

    fun getAntennaModelName(): String? {
        return printableName
    }
}