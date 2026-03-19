package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas

import com.lafabricadesoftware.rfidlaundry.presentation.configuracion.ConfiguracionEvent
import com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas.LecturaPrendasEvent

sealed class AsignacionPrendasEvent {
    object TestConnection: AsignacionPrendasEvent()
    object UpdateData: AsignacionPrendasEvent()
    object CleanAllData: AsignacionPrendasEvent()
    data class ShowMessage(val message: String): AsignacionPrendasEvent()
    data class ShowLoadingWithMessage(val show: Boolean, val message: String = "Cargando..."): AsignacionPrendasEvent()

    data class ShowAssignmentInitialDialog(val showDialog: Boolean): AsignacionPrendasEvent()
    data class GenerateAsignment(val barcode: String): AsignacionPrendasEvent()
    data class ShowAssignmentDoneDialog(val showDialog: Boolean): AsignacionPrendasEvent()

    data class EnteredBarcode(val value: String): AsignacionPrendasEvent()
}
