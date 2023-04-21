package pictograph.gateway

import io.fria.lilo.GraphQLRequest
import io.fria.lilo.Lilo
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
class GatewayController(private val lilo: Lilo) {

    @ResponseBody
    @PostMapping("/graphql")
    @NotNull
    suspend fun graphql(
        @RequestBody @NotNull request: GraphQLRequest,
        @RequestHeader(HttpHeaders.AUTHORIZATION) @NotNull authorizationHeader: String
    ): MutableMap<String, Any> {
        return lilo.stitchAwait(request, authorizationHeader).toSpecification()
    }
}