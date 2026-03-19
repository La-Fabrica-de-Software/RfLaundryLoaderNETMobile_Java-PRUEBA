package com.lafabricadesoftware.rfidlaundry.domain.util

sealed class TagOrder() {
    class ByNone(): TagOrder()
    class ByCliente(): TagOrder()
}
