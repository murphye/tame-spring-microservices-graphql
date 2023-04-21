package pictograph.user

import graphql.ExecutionInput
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class RequestHeaderInterceptor : WebGraphQlInterceptor {
    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        val value = request.headers.getFirst("tenant-id")
        if(value != null) {
            request.configureExecutionInput { executionInput: ExecutionInput, builder: ExecutionInput.Builder ->
                builder.graphQLContext(Collections.singletonMap<String, Any?>("tenantId", value)).build()
            }
        }
        return chain.next(request)
    }
}