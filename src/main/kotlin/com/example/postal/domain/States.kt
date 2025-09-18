package com.example.postal.domain

sealed interface State {
    val name: String
}

data object Received: State { override val name = "RECEIVED" }
data object InTransit: State { override val name = "IN_TRANSIT" }
data object OutForDelivery: State { override val name = "OUT_FOR_DELIVERY" }
data object Delivered: State { override val name = "DELIVERED" }
data object Lost: State { override val name = "LOST" }

val AllStates: List<State> = listOf(Received, InTransit, OutForDelivery, Delivered, Lost)
