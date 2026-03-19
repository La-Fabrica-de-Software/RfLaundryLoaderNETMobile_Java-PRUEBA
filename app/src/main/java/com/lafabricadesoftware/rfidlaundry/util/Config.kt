package com.lafabricadesoftware.rfidlaundry.util

import android.content.Context
import com.gitlab.mvysny.konsumexml.konsumeXml
import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.util.AntennaModel
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml
import java.io.File
import javax.inject.Inject

const val FOLDER_NAME = "files"
const val FILE_NAME = "configuration.xml"

class Config @Inject constructor(
    private val context: Context
) {
    private val fullPath = "${context.externalCacheDir}/$FOLDER_NAME/$FILE_NAME"

    private val printOptions = PrintOptions(pretty = true, singleLineTextElements = true)

    init {
        createFolderAndFile()
    }

    fun setConfiguracion(conf: Configuracion) {

        val file = File(fullPath)
        val content = xml("Configuracion") {
//            xmlns = "http://www.w3.org/2001/XMLSchema"
            "server" { -conf.server }
            "port" { -conf.port }
            "database" { -conf.database }
            "username" { -conf.username }
            "password" { -conf.password }
            "clientId" { -conf.clientId.toString() }
            "workstationId" { -conf.workstationId.toString() }
            "antennaModel" { -conf.antennaModel.toString() }
            "antennaPower" { -conf.antennaPower.toString() }
            "onlineOnly" { -conf.onlineOnly.toString() }
            "readBarcode" { -conf.readBarcode.toString() }
        }

        val configText = content.toString(printOptions)
        file.writeText(configText)
    }

    fun getConfiguracion(getDefault: Boolean): Configuracion {
        return try {
            if (getDefault) {
                getDefaultConfiguracion()
            } else {
                File(fullPath).konsumeXml().child("Configuracion") { Configuracion.getObject(this) }
            }
        } catch (e: Exception) {
            println(e.message)
            deleteAndCreateNewFileWithDefault()
            getConfiguracion(true)

//            if (getDefault) {
//                deleteAndCreateNewFileWithDefault()
//                getConfiguracion(true)
//            } else {
//                val conf = Configuracion()
//                conf
//            }
        }
    }

    private fun createFolderAndFile() {
        val path = context.externalCacheDir
        val folder = File("$path/$FOLDER_NAME")
        val file = File("$folder/$FILE_NAME")
        if (!folder.exists()) {
            folder.mkdir()
        }
        file.createNewFile()
    }

    private fun deleteAndCreateNewFileWithDefault() {
        val file = File(fullPath)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        setConfiguracion(getDefaultConfiguracion())
    }

    private fun getDefaultConfiguracion(): Configuracion {
        val configShowroom = Configuracion(
            server = "lfdsamazonrds.cwc1t3aqjwgt.eu-west-3.rds.amazonaws.com",
            port = "3433",
            database = "Laundry_ShowRoom",
            username = "lfs",
            password = "1Avand3r-\$",
            clientId = 0,
            workstationId = 0,
            antennaModel = AntennaModel.ChainwayC72,
            antennaPower = 50,
            onlineOnly = false,
            readBarcode = false
        )

        return configShowroom
    }
}