package pictograph.gateway

import graphql.ExecutionResult
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import io.fria.lilo.*
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

@Configuration
class LiloConfiguration {

    private val webClient: WebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    @Suppress("UNCHECKED_CAST")
    private fun introspectionRetriever(graphqlSchemaUrl: String) = AsyncIntrospectionRetriever { _, _, query, localContext -> retrieve(graphqlSchemaUrl, query,
        localContext as MultiValueMap<String, String>
    ) }

    @Suppress("UNCHECKED_CAST")
    private fun queryRetriever(graphqlQueryUrl: String) = AsyncQueryRetriever { _, _, query, localContext -> retrieve(graphqlQueryUrl, query.query,
        localContext as MultiValueMap<String, String>
    ) }

    @Bean
    fun lilo (
        @Value("\${lilo.graphqlurl.user}") userGraphQLUrl: String,
        @Value("\${lilo.graphqlurl.pic}")  picGraphQUrl: String,
        @Value("\${lilo.graphqlurl.like}") likeGraphQUrl: String,
        @Value("\${lilo.instrumentation.tracing}") enableTracingInstrumentation: Boolean
    ): Lilo {
        var builder = Lilo.builder().addSource(
            RemoteSchemaSource.create(
                "userService",
                introspectionRetriever(userGraphQLUrl),
                queryRetriever(userGraphQLUrl)
            )
        ).addSource(
            RemoteSchemaSource.create(
                "picService",
                introspectionRetriever(picGraphQUrl),
                queryRetriever(picGraphQUrl)
            )
        ).addSource(
            RemoteSchemaSource.create(
                "likeService",
                introspectionRetriever(likeGraphQUrl),
                queryRetriever(likeGraphQUrl)
            )
        )

        if(enableTracingInstrumentation) {
            builder = builder.instrumentation(TracingInstrumentation())
        }

        return builder.build()
    }

    /**
     * Query the GraphQL endpoint
     */
    private fun retrieve(graphqlUrl: String, query: String, headerMap: MultiValueMap<String, String>): CompletableFuture<String?> {
        val headers = Consumer<HttpHeaders> { it.addAll(headerMap) }
        return webClient
            .post()
            .uri(graphqlUrl)
            .headers(headers)
            .bodyValue(query)
            .retrieve()
            .toEntity(String::class.java)
            .mapNotNull { obj ->
                obj.body }.toFuture()
    }
}

/**
 * Helper for using Lilo within a suspending function.
 */
suspend fun Lilo.stitchAwait(request: GraphQLRequest, headers: MultiValueMap<String, String>): ExecutionResult {
    val resultFuture = this.stitchAsync(request.toExecutionInput(headers))
    return Mono.fromCompletionStage(resultFuture).awaitSingle()
}