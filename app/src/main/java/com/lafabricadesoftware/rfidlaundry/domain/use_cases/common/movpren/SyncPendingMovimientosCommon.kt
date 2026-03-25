package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren

import com.lafabricadesoftware.rfidlaundry.domain.model.MovPren
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import javax.inject.Inject

class SyncPendingMovimientosCommon @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(): Int {
        val pendientes = localRepository.getMovPrenPendientes()
        if (pendientes.isEmpty()) return 0

        var syncedCount = 0
        pendientes.forEach { pendiente ->
            try {
                val movPren = MovPren(
                    Id_Prenda = pendiente.Id_Prenda,
                    Fecha = pendiente.Fecha,
                    id_Puesto = pendiente.id_Puesto,
                    id_TipoAntena = pendiente.id_TipoAntena,
                    id_Operario = pendiente.id_Operario,
                    Obser = pendiente.Obser,
                    idCli = pendiente.idCli,
                    idSubCli = pendiente.idSubCli,
                    idModeloPrenda = pendiente.idModeloPrenda,
                    talla = pendiente.talla
                )
                val success = remoteRepository.setMovPrenSP(movPren)
                if (success) {
                    localRepository.deleteMovPrenPendiente(pendiente)
                    syncedCount++
                }
            } catch (e: Exception) {
                println("----- SyncPendingMovimientosCommon error syncing id=${pendiente.id}: ${e.message}")
            }
        }
        return syncedCount
    }
}
