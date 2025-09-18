package com.example.postal.repo

import com.example.postal.domain.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

data class PackageAggregate(
    val id: UUID,
    val history: List<Event>,
    val state: State
)

class InMemoryPackageRepo {
    private val data = ConcurrentHashMap<UUID, PackageAggregate>()

    fun create(initial: State = Received): PackageAggregate =
        UUID.randomUUID().let { id ->
            PackageAggregate(id, emptyList(), initial).also { data[id] = it }
        }

    fun get(id: UUID): PackageAggregate = data.getValue(id)

    fun list(): List<PackageAggregate> = data.values.sortedBy { it.id.toString() }

    fun apply(id: UUID, event: Event): PackageAggregate =
        get(id).let { current ->
            val nextState = Transitions.next(current.state, event)
            PackageAggregate(
                id = current.id,
                history = current.history + event,
                state = nextState
            ).also { data[id] = it }
        }
}
