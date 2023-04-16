package stichpics.gateway

import io.fria.lilo.GraphQLRequest
import io.fria.lilo.Lilo
import kotlinx.coroutines.reactor.awaitSingle
import org.jetbrains.annotations.NotNull
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
class GatewayController(private val lilo: Lilo) {

    @RequestMapping("/request")
    suspend fun getRequest(): String? {
        return ReactiveRequestContext.getHeadersAwait().getFirst("user")
    }

    @ResponseBody
    @PostMapping("/graphql")
    @NotNull
    suspend fun graphql(@RequestBody @NotNull request: GraphQLRequest): MutableMap<String, Any> {
        return lilo.stitchAwait(request).toSpecification()
    }
}