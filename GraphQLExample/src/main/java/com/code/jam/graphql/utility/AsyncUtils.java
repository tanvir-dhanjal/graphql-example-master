package com.code.jam.graphql.utility;

import graphql.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;

public final class AsyncUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(AsyncUtils.class);

    public static <T> void completeAsyncResponse(final Supplier<CompletionStage<T>> csSupplier,
                                                 final AsyncResponse asyncResponse) {
        try {
            csSupplier.get()
                    .thenApply(response -> {
                        Response responseBody;
                        if(response instanceof ExecutionResult) {
                            final ExecutionResult executionResult = (ExecutionResult) response;
                            if(executionResult.getErrors().size() == 0) {
                                responseBody= Response.status(OK).entity(executionResult.getData()).build();
                            } else {
                                responseBody= Response.status(INTERNAL_SERVER_ERROR).entity(executionResult.getErrors()).build();
                            }
                        } else {
                            responseBody = Response.status(OK).entity(response).build();
                        }
                        return  asyncResponse.resume(responseBody);
                    }).exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));
        } catch (Exception ex) {
            LOGGER.error(" Exception in processing the request");
        }
    }
}
