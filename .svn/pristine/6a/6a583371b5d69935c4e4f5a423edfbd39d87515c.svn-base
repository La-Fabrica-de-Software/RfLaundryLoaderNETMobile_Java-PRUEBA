package com.lafabricadesoftware.rfidlaundry.data.repository

import com.lafabricadesoftware.rfidlaundry.data.data_source.local.LocalDao
import com.lafabricadesoftware.rfidlaundry.domain.model.*
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.LecturaPrendaClienteSubCliente
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val dao: LocalDao
): LocalRepository {

    //region Clientes
    override fun getClientesAsFlow(): Flow<List<MasterClientes>> {
        return dao.getClientesAsFlow()
    }
    override suspend fun getClientes(): List<MasterClientes> {
        return dao.getClientes()
    }
    override suspend fun getClienteById(id: Int): MasterClientes {
        return dao.getClienteById(id)
    }
    override suspend fun insertCliente(cliente: MasterClientes) {
        return dao.insertCliente(cliente)
    }
    override suspend fun deleteCliente(cliente: MasterClientes) {
        return dao.deleteCliente(cliente)
    }
    override suspend fun deleteClientes() {
        return dao.deleteClientes()
    }
    //endregion

    //region SubClientes
    override fun getSubClientesAsFlow(): Flow<List<MasterSubClientes>> {
        return dao.getSubClientesAsFlow()
    }
    override suspend fun getSubClientes(idCliente: Int): List<MasterSubClientes> {
        if (idCliente > 0) {
            return dao.getSubClientesByIdCliente(idCliente)
        } else {
            return dao.getSubClientes()
        }
    }
    override suspend fun getSubClienteById(id: Int): MasterSubClientes {
        return dao.getSubClienteById(id)
    }
    override suspend fun insertSubCliente(subCliente: MasterSubClientes) {
        return dao.insertSubCliente(subCliente)
    }
    override suspend fun deleteSubCliente(subCliente: MasterSubClientes) {
        return dao.deleteSubCliente(subCliente)
    }
    override suspend fun deleteSubClientes() {
        return dao.deleteSubClientes()
    }
    //endregion

    //region Prendas
    override fun getPrendasAsFlow(): Flow<List<MasterPrendas>> {
        return dao.getPrendasAsFlow()
    }
    override suspend fun getPrendas(idSubCliente: Int): List<MasterPrendas> {
        if (idSubCliente > 0) {
            return dao.getPrendasByIdSubCliente(idSubCliente)
        } else {
            return dao.getPrendas()
        }
    }
    override suspend fun getPrendaById(id: Int): MasterPrendas {
        return dao.getPrendaById(id)
    }
    override suspend fun getPrendaByTag(tag: String): MasterPrendas {
        return dao.getPrendaByTag(tag)
    }
    override suspend fun insertPrenda(prenda: MasterPrendas) {
        return dao.insertPrenda(prenda)
    }
    override suspend fun deletePrenda(prenda: MasterPrendas) {
        return dao.deletePrenda(prenda)
    }
    override suspend fun deletePrendas() {
        return dao.deletePrendas()
    }
    //endregion

    //region Modelo prenda
    override suspend fun getModeloPrenda(id: Int): MasterModeloPrenda {
        return dao.getModeloPrenda(id)
    }
    //endregion

    //region Antenas
    override fun getAntenasAsFlow(): Flow<List<MasterTipoAntena>> {
        return dao.getAntenasAsFlow()
    }
    override suspend fun getAntenas(): List<MasterTipoAntena> {
        return dao.getAntenas()
    }
    override fun getAntenasPuestosAsFlow(): Flow<List<MovMostrarAntenasPuesto>> {
        return dao.getAntenasPuestosAsFlow()
    }
    override suspend fun getAntenasPuestos(): List<MovMostrarAntenasPuesto> {
        return dao.getAntenasPuestos()
    }
    //endregion

    //region MovPren
//    override fun getMovPrenAsFlow(): Flow<List<MovPren>> {
//        return dao.getMovPrenAsFlow()
//    }
    override suspend fun getMovPren(id: Int): MovPren {
        return dao.getMovPren(id)
    }
    override suspend fun getLastMovPrenByIdPrenda(idPrenda: Int): MovPren {
        return dao.getLastMovPrenByIdPrenda(idPrenda)
    }
    //endregion

    //region Taquillas
    override fun getTaquillasAsFlow(): Flow<List<MasterTaquilla>> {
        return dao.getTaquillasAsFlow()
    }
    override suspend fun getTaquillas(): List<MasterTaquilla> {
        return dao.getTaquillas()
    }
    override suspend fun getTaquillaByDescripcion(descripcion: String): MasterTaquilla {
        return dao.getTaquillaByDescricion(descripcion)
    }
    //endregion
}