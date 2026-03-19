package com.lafabricadesoftware.rfidlaundry.presentation.asignacion_prendas

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterTipoAntena
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.Prenda
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.PrendaCliente
import com.lafabricadesoftware.rfidlaundry.domain.util.EnteredBarcodeStatus
import com.lafabricadesoftware.rfidlaundry.domain.util.TagOrder

data class AsignacionPrendasState(
    val clientsList: List<PrendaCliente> = listOf(),
    val unknownGarmentsList: List<Prenda> = listOf(),
    val movementsList: List<MasterTipoAntena> = listOf(),
    val selectedMovement: Int = 0,
    val enableAssignmentButton: Boolean = false,
    val total: Int = 0,
    val unique: Int = 0,
    val toBeChecked: Int = 0,
    val group: Boolean = false,
    val order: TagOrder = TagOrder.ByNone(),
    val connectionStatus: Boolean = false,
    val showAssignmentInitialDialog: Boolean = false,
    val showAssignmentDoneDialog: Boolean = false,
    val dialogText: String = "",
//    val movementInformationMessage: String = "",
    val showLoadingMessage: Boolean = false,
    val loadingMessage: String = "",
    val showMessage: Boolean = false,
    val message: String = "",
    val selectedClientId: Int = 0,
    val selectedSubClientId: Int = 0,
//    val hideMovementDismissConfirmButtons: Boolean = false,
    val goToConfig: Boolean = false,
    val barcode: String = "",
    val barcodeStatus: EnteredBarcodeStatus = EnteredBarcodeStatus.Idle
) {}