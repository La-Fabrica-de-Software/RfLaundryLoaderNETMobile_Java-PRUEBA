package com.lafabricadesoftware.rfidlaundry.data.data_source.local

import androidx.room.*
import com.lafabricadesoftware.rfidlaundry.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    //region Configuración

//    @Query("SELECT * FROM Configuracion WHERE id = 1")
//    suspend fun getConfiguration(): Configuracion
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun setConfiguration(configuracion: Configuracion)
//
//    @Query("DELETE FROM Configuracion")
//    suspend fun deleteConfigurations()

    //endregion

    //region MasterClientes
    @Query("SELECT * FROM MasterClientes")
    fun getClientesAsFlow(): Flow<List<MasterClientes>>
    @Query("SELECT * FROM MasterClientes")
    suspend fun getClientes(): List<MasterClientes>
    @Query("SELECT * FROM MasterClientes WHERE id = :id")
    suspend fun getClienteById(id: Int): MasterClientes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(cliente: MasterClientes)
    @Delete
    suspend fun deleteCliente(cliente: MasterClientes)
    @Query("DELETE FROM MasterClientes")
    suspend fun deleteClientes()
    //endregion

    //region MasterSubClientes
    @Query("SELECT * FROM MasterSubClientes")
    fun getSubClientesAsFlow(): Flow<List<MasterSubClientes>>
    @Query("SELECT * FROM MasterSubClientes")
    suspend fun getSubClientes(): List<MasterSubClientes>
    @Query("SELECT * FROM MasterSubClientes WHERE id_cliente = :idCliente")
    suspend fun getSubClientesByIdCliente(idCliente: Int): List<MasterSubClientes>
    @Query("SELECT * FROM MasterSubClientes WHERE id = :id")
    suspend fun getSubClienteById(id: Int): MasterSubClientes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCliente(subCliente: MasterSubClientes)
    @Delete
    suspend fun deleteSubCliente(subCliente: MasterSubClientes)
    @Query("DELETE FROM MasterSubClientes")
    suspend fun deleteSubClientes()
    //endregion

    //region MasterPrendas
    @Query("SELECT * FROM MasterModeloPrenda WHERE id = :id")
    suspend fun getModeloPrenda(id: Int): MasterModeloPrenda
    //endregion

    //region MasterPrendas
    @Query("SELECT * FROM MasterPrendas")
    fun getPrendasAsFlow(): Flow<List<MasterPrendas>>
    @Query("SELECT * FROM MasterPrendas")
    fun getPrendas(): List<MasterPrendas>
    @Query("SELECT * FROM MasterPrendas WHERE id_Subcliente = :idSubCliente")
    fun getPrendasByIdSubCliente(idSubCliente: Int): List<MasterPrendas>
    @Query("SELECT * FROM MasterPrendas WHERE Id = :id")
    suspend fun getPrendaById(id: Int): MasterPrendas
    @Query("SELECT * FROM MasterPrendas WHERE codigoTAG = :tag")
    suspend fun getPrendaByTag(tag: String): MasterPrendas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrenda(prenda: MasterPrendas)
    @Delete
    suspend fun deletePrenda(prenda: MasterPrendas)
    @Query("DELETE FROM MasterPrendas")
    suspend fun deletePrendas()
    //endregion

    //region Antenas
    @Query("SELECT * FROM MasterTipoAntena")
    fun getAntenasAsFlow(): Flow<List<MasterTipoAntena>>
    @Query("SELECT * FROM MasterTipoAntena")
    suspend fun getAntenas(): List<MasterTipoAntena>
    @Query("SELECT * FROM MovMostrarAntenasPuesto")
    fun getAntenasPuestosAsFlow(): Flow<List<MovMostrarAntenasPuesto>>
    @Query("SELECT * FROM MovMostrarAntenasPuesto")
    suspend fun getAntenasPuestos(): List<MovMostrarAntenasPuesto>
    //endregion

    //region MovPren
    @Query("SELECT * FROM MovPren WHERE Id = :id")
    suspend fun getMovPren(id: Int): MovPren
    @Query("SELECT * FROM MovPren WHERE Id_Prenda = :idPrenda ORDER BY Fecha DESC LIMIT 1")
    suspend fun getLastMovPrenByIdPrenda(idPrenda: Int): MovPren
    //endregion

    //region MasterTaquilla
    @Query("SELECT * FROM MasterTaquilla")
    fun getTaquillasAsFlow(): Flow<List<MasterTaquilla>>
    @Query("SELECT * FROM MasterTaquilla")
    suspend fun getTaquillas(): List<MasterTaquilla>
    @Query("SELECT * FROM MasterTaquilla WHERE Descripcion = :descripcion ORDER BY Id DESC LIMIT 1")
    suspend fun getTaquillaByDescricion(descripcion: String): MasterTaquilla
    //endregion
}