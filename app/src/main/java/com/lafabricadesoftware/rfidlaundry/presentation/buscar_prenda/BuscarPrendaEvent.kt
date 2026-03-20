package com.lafabricadesoftware.rfidlaundry.presentation.buscar_prenda

import com.lafabricadesoftware.rfidlaundry.domain.model.MasterClientes
import com.lafabricadesoftware.rfidlaundry.domain.model.MasterModeloPrenda

sealed class BuscarPrendaEvent {
    object LoadClientes : BuscarPrendaEvent()
    data class SelectCliente(val cliente: MasterClientes?) : BuscarPrendaEvent()
    data class SelectModelo(val modelo: MasterModeloPrenda?) : BuscarPrendaEvent()
    data class SelectTalla(val talla: String) : BuscarPrendaEvent()
    object StartReading : BuscarPrendaEvent()
    object StopReading : BuscarPrendaEvent()
    object ResetFound : BuscarPrendaEvent()
}
