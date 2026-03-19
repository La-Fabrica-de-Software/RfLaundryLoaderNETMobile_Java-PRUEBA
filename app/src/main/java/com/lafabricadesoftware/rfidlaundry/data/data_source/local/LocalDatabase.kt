package com.lafabricadesoftware.rfidlaundry.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lafabricadesoftware.rfidlaundry.domain.model.*

@Database(
    entities = [
//        Configuracion::class,
        MasterClientes::class,
        MasterSubClientes::class,
        MasterPrendas::class,
        MasterModeloPrenda::class,
        MasterTipoAntena::class,
        MovMostrarAntenasPuesto::class,
        MovPren::class,
        MasterTaquilla::class
    ],
    version = 13,
    exportSchema = false
)

abstract class LocalDatabase: RoomDatabase() {

    abstract val localDao: LocalDao

    companion object {
        const val DATABASE_NAME = "local_db"
    }
}