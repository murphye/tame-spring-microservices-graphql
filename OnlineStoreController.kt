package com.estore

import io.fria.lilo.GraphQLRequest
import io.fria.lilo.Lilo
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OnlineStoreController(private val lilo: Lilo) {

    @ResponseBody
    @PostMapping("/graphql")
    @NotNull
    suspend fun graphql(@RequestBody @NotNull request: GraphQLRequest): MutableMap<String, Any> {
        return lilo.stitchAwait(request).toSpecification()
    }
}