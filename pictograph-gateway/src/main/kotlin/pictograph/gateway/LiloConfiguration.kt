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

@Configuration
class LiloConfiguration {

    private val webClient: WebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    private fun introspectionRetriever(graphqlSchemaUrl: String) = AsyncIntrospectionRetriever { _, _, query, _ -> get(graphqlSchemaUrl, query) }

    private fun queryRetriever(graphqlQueryUrl: String) = AsyncQueryRetriever { _, _, query, _ -> get(graphqlQueryUrl, query.query) }

    @Bean
    fun lilo (
        @Value("\${lilo.url.user}") userUrl: String,
        @Value("\${lilo.url.pic}")  picUrl: String,
        @Value("\${lilo.url.like}") likeUrl: String
    ): Lilo {
        return Lilo.builder().addSource(
            RemoteSchemaSource.create(
                "userService",
                introspectionRetriever(userUrl),
                queryRetriever(userUrl)
            )
        ).addSource(
            RemoteSchemaSource.create(
                "picService",
                introspectionRetriever(picUrl),
                queryRetriever(picUrl)
            )
        ).addSource(
            RemoteSchemaSource.create(
                "likeService",
                introspectionRetriever(likeUrl),
                queryRetriever(likeUrl)
            )
        ).build()
    }

    /**
     * Query the GraphQL endpoint
     */
    private fun get(graphqlUrl: String, query: String): CompletableFuture<String?> {
        return webClient
            .post()
            .uri(graphqlUrl)
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
suspend fun Lilo.stitchAwait(request: GraphQLRequest): ExecutionResult {
    val resultFuture = this.stitchAsync(request.toExecutionInput())
    return Mono.fromCompletionStage(resultFuture).awaitSingle()
}