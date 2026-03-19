package com.lafabricadesoftware.rfidlaundry.presentation.lectura_prendas

sealed class LecturaPrendasEvent {
    object TestConnection: LecturaPrendasEvent()
    object UpdateData: LecturaPrendasEvent()
    object CleanAllData: LecturaPrendasEvent()
    data class GroupData(val group: Boolean): LecturaPrendasEvent()
    data class ShowMessage(val message: String): LecturaPrendasEvent()
    data class ShowLoadingWithMessage(val show: Boolean, val message: String = "Cargando..."): LecturaPrendasEvent()
    data class ShowMovementsDialog(val showDialog: Boolean): LecturaPrendasEvent()
    data class ShowMovementInformationDialog(val showDialog: Boolean, val antennaId: Int): LecturaPrendasEvent()
    data class GenerateMovement(val antennaId: Int, val clientId: Int, val subClientId: Int): LecturaPrendasEvent()
    object Order: LecturaPrendasEvent()
    object SelectTag: LecturaPrendasEvent()
    object ToggleOrderSection: LecturaPrendasEvent()
}
