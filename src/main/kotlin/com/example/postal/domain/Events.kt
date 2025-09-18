package com.example.postal.domain

sealed interface Event { val name: String }

data object Accept: Event { override val name = "ACCEPT" }
data object Dispatch: Event { override val name = "DISPATCH" }
data object StartDelivery: Event { override val name = "START_DELIVERY" }
data object ConfirmDelivery: Event { override val name = "CONFIRM_DELIVERY" }
data object MarkLost: Event { override val name = "MARK_LOST" }

val AllEvents: List<Event> = listOf(Accept, Dispatch, StartDelivery, ConfirmDelivery, MarkLost)
val EventByName: Map<String, Event> = AllEvents.associateBy { it.name }
