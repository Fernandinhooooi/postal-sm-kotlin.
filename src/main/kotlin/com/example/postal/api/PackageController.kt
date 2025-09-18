package com.example.postal.api

import com.example.postal.domain.EventByName
import com.example.postal.repo.InMemoryPackageRepo
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/packages")
class PackageController(
    private val repo: InMemoryPackageRepo = InMemoryPackageRepo()
) {
    data class CreateResponse(val id: UUID, val state: String)
    data class ViewResponse(val id: UUID, val state: String, val history: List<String>)

    @PostMapping
    fun create(): CreateResponse =
        repo.create().let { CreateResponse(it.id, it.state.name) }

    @GetMapping
    fun list(): List<ViewResponse> =
        repo.list().map { ViewResponse(it.id, it.state.name, it.history.map { e -> e.name }) }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ViewResponse =
        repo.get(id).let { ViewResponse(it.id, it.state.name, it.history.map { e -> e.name }) }

    @PostMapping("/{id}/events/{eventName}")
    fun apply(
        @PathVariable id: UUID,
        @PathVariable eventName: String
    ): ViewResponse =
        EventByName.getValue(eventName.uppercase()).let { ev ->
            repo.apply(id, ev).let {
                ViewResponse(it.id, it.state.name, it.history.map { e -> e.name })
            }
        }
}
