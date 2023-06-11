package org.lnu.timetable.interceptor;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static org.lnu.timetable.constants.GraphQlContextConstants.AUTH_TOKEN;

@Component
public class GraphQlAuthInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        return chain.next(request).doOnNext(response -> {
            String authToken = response.getExecutionInput().getGraphQLContext().get(AUTH_TOKEN);
            if (authToken != null) {
                response.getResponseHeaders().add(HttpHeaders.AUTHORIZATION, authToken);
            }
        });
    }
}
