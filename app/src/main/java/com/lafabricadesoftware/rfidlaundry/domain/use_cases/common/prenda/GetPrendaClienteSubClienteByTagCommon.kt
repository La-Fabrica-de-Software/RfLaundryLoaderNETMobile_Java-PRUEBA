package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.Configuracion
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterPrendas
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.LecturaPrendaClienteSubCliente
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPrendaClienteSubClienteByTagCommon @Inject constructor (
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(tag: String, useLocal: Boolean = false, config: Configuracion): LecturaPrendaClienteSubCliente = withContext(defaultDispatcher) {
        var prendaClienteSubCliente = LecturaPrendaClienteSubCliente(
            tag = tag,
            isExiste = false,
            isBorrado = false
        )
        try {
            if (useLocal) {
                try {
                    val prenda = localRepository.getPrendaByTag(tag)
                    val cliente = localRepository.getClienteById(prenda.id_Cliente)
                    val subCliente = localRepository.getSubClienteById(prenda.id_subCliente)
                    val modeloPrenda = localRepository.getModeloPrenda(prenda.id_ModeloPrenda)
                    val lastMovPrenda = localRepository.getLastMovPrenByIdPrenda(prenda.Id)

                    val localPrenda = LecturaPrendaClienteSubCliente(
                        idPrenda = prenda.Id,
                        nombrePrenda = prenda.descrip,
                        tag = tag,
                        isExiste = true,
                        isBorrado = prenda.borrado,
                        idCliente = prenda.id_Cliente,
                        nombreCliente = cliente.Nombre,
                        clienteBorrado = cliente.Borrado,
                        idSubCliente = prenda.id_subCliente,
                        nombreSubCliente = String.format("%s %s", subCliente.Nombre, subCliente.Apellido1),
                        subClienteBorrado = subCliente.Borrado,
                        idModeloPrenda = prenda.id_ModeloPrenda,
                        nombreModeloPrenda = modeloPrenda.Descripcion,
                        idLastMov = lastMovPrenda.Id,
                        lastMovAntena = lastMovPrenda.id_TipoAntena,
                        lastMovFecha = lastMovPrenda.Fecha,
                        lastMov = prenda.LastMov,
                        vestuarioSubCliente = subCliente.Vestuario
                    )
                    if (localPrenda.idPrenda != 0) {
                        prendaClienteSubCliente = localPrenda
                    }
                    return@withContext prendaClienteSubCliente
                } catch (e: Exception) {
                    throw e
                }
            } else {
                try {
                    val remotePrenda = remoteRepository.getPrendaClienteSubClienteByTag(tag)
                    if (remotePrenda.idPrenda != 0) {
                        prendaClienteSubCliente = remotePrenda
                    }
                    return@withContext prendaClienteSubCliente
                } catch (e: Exception) {
                    throw e
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
