package com.lafabricadesoftware.rfidlaundry.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        MasterTaquilla::class,
        MovPrenPendiente::class
    ],
    version = 14,
    exportSchema = false
)

abstract class LocalDatabase: RoomDatabase() {

    abstract val localDao: LocalDao

    companion object {
        const val DATABASE_NAME = "local_db"

        val MIGRATION_13_14 = object : Migration(13, 14) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `MovPrenPendiente` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`Id_Prenda` INTEGER NOT NULL, " +
                    "`Fecha` TEXT NOT NULL, " +
                    "`id_Puesto` INTEGER NOT NULL, " +
                    "`id_TipoAntena` INTEGER NOT NULL, " +
                    "`id_Operario` TEXT NOT NULL, " +
                    "`Obser` TEXT NOT NULL, " +
                    "`idCli` INTEGER NOT NULL, " +
                    "`idSubCli` INTEGER NOT NULL, " +
                    "`idModeloPrenda` INTEGER NOT NULL, " +
                    "`talla` TEXT NOT NULL)"
                )
            }
        }
    }
}