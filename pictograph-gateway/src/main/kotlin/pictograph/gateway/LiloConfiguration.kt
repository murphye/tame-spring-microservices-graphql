package pictograph.gateway

import graphql.ExecutionResult
import io.fria.lilo.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

/**
 * Build Lilo from a comma-delimited configuration Value of "lilo.graphql-urls" in the
 * application.properties/yaml. Additionally, for incoming requests, the HTTP headers will
 * be automatically propagated to the upstream GraphQL endpoints.
 */
@Configuration
class LiloConfiguration {

    private val webClient: WebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    @Bean
    fun lilo(@Value("\${lilo.graphql-urls}") graphqlUrls: Array<String>): Lilo {
        var liloBuilder = Lilo.builder()

        for(graphqlUrl in graphqlUrls) {
            val introspectionRetriever = AsyncIntrospectionRetriever { _, _, query, _ -> get(graphqlUrl, query) }
            val queryRetriever = AsyncQueryRetriever { _, _, query, _ -> get(graphqlUrl, query.query) }

            liloBuilder = liloBuilder.addSource(
                RemoteSchemaSource.create(
                    graphqlUrl.split("://").last(), // Make the schemaName from the URL (for simplicity)
                    introspectionRetriever,
                    queryRetriever
                    ))
        }

        return liloBuilder.build()
    }

    /**
     * Query the GraphQL endpoint while propagating the HTTP headers from the original request.
     */
    private fun get(graphqlUrl: String, query: String): CompletableFuture<String?> {
        return ReactiveRequestContext.getHeaders().flatMap { headers ->
             webClient
                .post()
                .uri(graphqlUrl)
                .headers {
                    it.addAll(headers)
                }
                .bodyValue(query)
                .retrieve()
                .toEntity(String::class.java)
                .mapNotNull { obj -> obj.body }
        }.toFuture()
    }
}

/**
 * Helper for using Lilo within a suspending function.
 */
suspend fun Lilo.stitchAwait(request: GraphQLRequest): ExecutionResult {
    val resultFuture = this.stitchAsync(request.toExecutionInput())
    return Mono.fromCompletionStage(resultFuture).awaitSingle()
}