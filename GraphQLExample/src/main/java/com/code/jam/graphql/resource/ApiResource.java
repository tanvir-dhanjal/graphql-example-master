package com.code.jam.graphql.resource;

import com.code.jam.graphql.configs.GraphQlConfig;
import graphql.ExecutionInput;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.code.jam.graphql.utility.AsyncUtils.completeAsyncResponse;

@Path("/v1")
@Resource
public class ApiResource {

    @Autowired
    private GraphQlConfig graphQlConfig;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/healthcheck")
    public void hello(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.resume(Response.ok().entity("Service is UP").build());
    }

    @POST
    @Path("/graphql")
    @Produces(MediaType.APPLICATION_JSON)
    public void runGraphQLQuery(@Suspended final AsyncResponse asyncResponse,
                                final String graphQlQuery) {
        completeAsyncResponse(() -> graphQlConfig.graphQL().executeAsync(ExecutionInput
                .newExecutionInput()
                .query(graphQlQuery)
                .build()), asyncResponse);
    }
}
