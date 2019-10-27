package com.code.jam.graphql.configs;

import com.code.jam.graphql.wiring.GraphQlRuntimeWiring;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

@Configuration
public class GraphQlConfig {

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        final URL url = Resources.getResource("schema.graphqls");
        final String sdl = Resources.toString(url, Charsets.UTF_8);
        final GraphQlRuntimeWiring graphQlRuntimeWiring = new GraphQlRuntimeWiring();
        final SchemaGenerator schemaGenerator = new SchemaGenerator();

        this.graphQL = GraphQL.newGraphQL(schemaGenerator
                .makeExecutableSchema(new SchemaParser().parse(sdl), graphQlRuntimeWiring.buildRuntimeWiring()))
                .build();
    }
}
