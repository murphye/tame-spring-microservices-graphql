package stichpics.gateway

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ReactiveRequestContext : WebFilter {

    /**
     * Add the incoming ServerHttpRequest to the current reactive Context. The request will be
     * available later for accessing the HttpHeaders or other data. This uses Reactor Context
     * Propagation and replaces how ThreadLocal could be used to store the context of a request.
     */
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request
        return chain.filter(exchange)
            .contextWrite { ctx -> ctx.put(ServerHttpRequest::class.java, request) }
    }

    companion object {
        /**
         * Capture the HttpHeaders from the current reactive Context
         */
        fun getHeaders() = Mono.deferContextual { context ->
            // Return the ServerHttpRequest from the reactive Context
            context.get(ServerHttpRequest::class.java).toMono()
        }.mapNotNull { httpRequest ->
            // Return the HttpHeaders from the ServerHttpRequest
            httpRequest.headers
        }

        suspend fun getHeadersAwait() = getHeaders().awaitSingle()
    }
}