package pictograph.user

import graphql.GraphQL
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.graphql.v12_0.GraphQLTelemetry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.GraphQlSource.SchemaResourceBuilder


@Configuration(proxyBeanMethods = false)
internal class GraphQlConfig {
    @Bean
    fun sourceBuilderCustomizer(@Autowired openTelemetry: OpenTelemetry): GraphQlSourceBuilderCustomizer {
        return GraphQlSourceBuilderCustomizer { builder: SchemaResourceBuilder ->
            builder.configureGraphQl { graphQlBuilder: GraphQL.Builder ->
                val graphQLTelemetry = GraphQLTelemetry.builder(openTelemetry).build()
                graphQlBuilder.instrumentation(graphQLTelemetry.newInstrumentation())
            }
        }
    }
}