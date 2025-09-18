package com.example.postal.domain

object Transitions {
    private val table: Map<State, Map<Event, State>> = mapOf(
        Received to mapOf(
            Dispatch to InTransit,
            MarkLost to Lost
        ),
        InTransit to mapOf(
            StartDelivery to OutForDelivery,
            MarkLost to Lost
        ),
        OutForDelivery to mapOf(
            ConfirmDelivery to Delivered,
            MarkLost to Lost
        ),
        Delivered to emptyMap(),
        Lost to emptyMap()
    )

    fun next(from: State, event: Event): State =
        table.getValue(from).getValue(event)
}
