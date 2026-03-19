package com.lafabricadesoftware.rfidlaundry.domain.util

sealed class ClienteOrder(val orderType: OrderType) {
    class ById(orderType: OrderType): ClienteOrder(orderType)
    class ByNombre(orderType: OrderType): ClienteOrder(orderType)

    fun copy(orderType: OrderType): ClienteOrder {
        return when(this) {
            is ById -> ById(orderType)
            is ByNombre -> ByNombre(orderType)
        }
    }
}
