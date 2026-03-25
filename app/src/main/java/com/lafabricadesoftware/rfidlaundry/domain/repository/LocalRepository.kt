package com.lafabricadesoftware.rfidlaundry.domain.repository

import com.lafabricadesoftware.rfidlaundry.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    //region Configuracion
//    suspend fun getConfiguration(): Configuracion
//    suspend fun setConfiguration(configuracion: Configuracion)
//    suspend fun deleteConfigurations()
    //endregion

    //region Clientes
    fun getClientesAsFlow(): Flow<List<MasterClientes>>
    suspend fun getClientes(): List<MasterClientes>
    suspend fun getClienteById(id: Int): MasterClientes
    suspend fun insertCliente(cliente: MasterClientes)
    suspend fun deleteCliente(cliente: MasterClientes)
    suspend fun deleteClientes()
    //endregion

    //region SubClientes
    fun getSubClientesAsFlow(): Flow<List<MasterSubClientes>>
    suspend fun getSubClientes(idCliente: Int): List<MasterSubClientes>
    suspend fun getSubClienteById(id: Int): MasterSubClientes
    suspend fun insertSubCliente(subCliente: MasterSubClientes)
    suspend fun deleteSubCliente(subCliente: MasterSubClientes)
    suspend fun deleteSubClientes()
    //endregion

    //region Prendas
    fun getPrendasAsFlow(): Flow<List<MasterPrendas>>
    suspend fun getPrendas(idSubCliente: Int): List<MasterPrendas>
    suspend fun getPrendaById(id: Int): MasterPrendas
    suspend fun getPrendaByTag(tag: String): MasterPrendas
//    suspend fun getPrendaClienteSubClienteByTag(tag: String): LecturaPrendaClienteSubCliente
    suspend fun insertPrenda(prenda: MasterPrendas)
    suspend fun deletePrenda(prenda: MasterPrendas)
    suspend fun deletePrendas()
    //endregion

    //region ModeloPrenda
    suspend fun getModeloPrenda(id: Int): MasterModeloPrenda
    //endregion

    //region Antenas
    fun getAntenasAsFlow(): Flow<List<MasterTipoAntena>>
    suspend fun getAntenas(): List<MasterTipoAntena>
    fun getAntenasPuestosAsFlow(): Flow<List<MovMostrarAntenasPuesto>>
    suspend fun getAntenasPuestos(): List<MovMostrarAntenasPuesto>
    //endregion

    //region MovPren
    suspend fun getMovPren(id: Int): MovPren
    suspend fun getLastMovPrenByIdPrenda(idPrenda: Int): MovPren
    //endregion

    //region Taquillas
    fun getTaquillasAsFlow(): Flow<List<MasterTaquilla>>
    suspend fun getTaquillas(): List<MasterTaquilla>
    suspend fun getTaquillaByDescripcion(descripcion: String): MasterTaquilla
    //endregion

    //region MovPrenPendiente
    suspend fun getMovPrenPendientes(): List<MovPrenPendiente>
    fun getMovPrenPendientesCountAsFlow(): Flow<Int>
    suspend fun insertMovPrenPendiente(movPren: MovPrenPendiente)
    suspend fun deleteMovPrenPendiente(movPren: MovPrenPendiente)
    //endregion
}