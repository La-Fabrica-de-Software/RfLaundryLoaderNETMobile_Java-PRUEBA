package com.lafabricadesoftware.rfidlaundry.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
