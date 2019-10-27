package com.code.jam.graphql.configs;

import com.code.jam.graphql.resource.ApiResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ApiResource.class);
    }
}