package stichpics.gateway

import io.fria.lilo.GraphQLRequest
import io.fria.lilo.Lilo
import kotlinx.coroutines.reactor.awaitSingle
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.*

@RestController
class GatewayController(private val lilo: Lilo) {

    @RequestMapping("/request")
    suspend fun getRequest(): String? {
        return ReactiveRequestContext.getHeaders().awaitSingle().getFirst("user")
    }

    @ResponseBody
    @PostMapping("/graphql")
    @NotNull
    suspend fun graphql(@RequestBody @NotNull request: GraphQLRequest): MutableMap<String, Any> {
        return lilo.stitchAwait(request).toSpecification()
    }
}