package com.estore

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
 * Build Lilo from a comma-delimited configuration Value of "lilo.schema-urls"
 */
@Configuration
class LiloConfiguration {

    private val webClient: WebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    @Bean
    fun lilo(@Value("\${lilo.schema-urls}") schemaUrls: Array<String>): Lilo {
        var liloBuilder = Lilo.builder()

        for(schemaUrl in schemaUrls) {
            val queryRetriever = AsyncQueryRetriever { _, _, query, _ -> get(schemaUrl, query.query) }
            val introspectionRetriever = AsyncIntrospectionRetriever { _, _, query, _ -> get(schemaUrl, query) }

            liloBuilder = liloBuilder.addSource(
                RemoteSchemaSource.create(
                    schemaUrl.split("://").last(), // Make the schemaName from the URL (for simplicity)
                    introspectionRetriever,
                    queryRetriever
                    ))
        }

        return liloBuilder.build()
    }

    private fun get(schemaUrl: String, query: String): CompletableFuture<String?> {
        return webClient
            .post()
            .uri("$schemaUrl/graphql")
            .bodyValue(query)
            .retrieve()
            .toEntity(String::class.java)
            .mapNotNull { obj -> obj.body }
            .toFuture()
    }
}

suspend fun Lilo.stitchAwait(request: GraphQLRequest): ExecutionResult {
    val resultFuture = this.stitchAsync(request.toExecutionInput())
    return Mono.fromCompletionStage(resultFuture).awaitSingle()
}