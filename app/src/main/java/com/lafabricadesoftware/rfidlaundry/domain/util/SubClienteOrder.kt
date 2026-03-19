package com.lafabricadesoftware.rfidlaundry.domain.util

sealed class SubClienteOrder(val orderType: OrderType) {
    class ById(orderType: OrderType): SubClienteOrder(orderType)
    class ByNombre(orderType: OrderType): SubClienteOrder(orderType)

    fun copy(orderType: OrderType): SubClienteOrder {
        return when(this) {
            is ById -> ById(orderType)
            is ByNombre -> ByNombre(orderType)
        }
    }
}
