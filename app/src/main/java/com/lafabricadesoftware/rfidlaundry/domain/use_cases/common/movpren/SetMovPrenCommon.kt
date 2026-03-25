package com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren

import com.lafabricadesoftware.rfidlaundry.domain.model.MovPren
import com.lafabricadesoftware.rfidlaundry.domain.model.MovPrenPendiente
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.Prenda
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

class SetMovPrenCommon @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(
        antennaId: Int,
        clientId: Int,
        subClientId: Int,
        prendasForMovement: List<Prenda>,
        useSP: Boolean = true
    ) {

        val validOnlineConnection = runBlocking {
            remoteRepository.testConnection()
        }

        prendasForMovement.forEach {
            val fecha = LocalDate.now().atStartOfDay().toString()
            val movPren = MovPren(
                Id_Prenda = it.idPrenda,
                Fecha = fecha,
                id_Puesto = 0,
                id_TipoAntena = antennaId,
                idCli = clientId,
                idSubCli = subClientId,
                idModeloPrenda = it.idModeloPrenda,
                talla = it.talla
            )
            try {
                if (useSP && validOnlineConnection) {
                    remoteRepository.setMovPrenSP(movPren)
                } else {
                    if (!validOnlineConnection) {
                        runBlocking {
                            localRepository.insertMovPrenPendiente(
                                MovPrenPendiente(
                                    Id_Prenda = it.idPrenda,
                                    Fecha = fecha,
                                    id_Puesto = 0,
                                    id_TipoAntena = antennaId,
                                    idCli = clientId,
                                    idSubCli = subClientId,
                                    idModeloPrenda = it.idModeloPrenda,
                                    talla = it.talla
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                runBlocking {
                    localRepository.insertMovPrenPendiente(
                        MovPrenPendiente(
                            Id_Prenda = it.idPrenda,
                            Fecha = fecha,
                            id_Puesto = 0,
                            id_TipoAntena = antennaId,
                            idCli = clientId,
                            idSubCli = subClientId,
                            idModeloPrenda = it.idModeloPrenda,
                            talla = it.talla
                        )
                    )
                }
            }
        }
    }
}