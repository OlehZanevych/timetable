package org.lnu.timetable.filter;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.lnu.timetable.constants.ApiConstants.AUTH_HEADER;

@Component
@AllArgsConstructor
public class CorsFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        HttpHeaders responseHeaders = response.getHeaders();

        addCorsHeaders(responseHeaders);

        return webFilterChain.filter(serverWebExchange);
    }

    private void addCorsHeaders(HttpHeaders responseHeaders) {
        responseHeaders.add("Access-Control-Allow-Origin", "*");
        responseHeaders.add("Access-Control-Allow-Headers", "Origin,Content-Type,Accept," +
            "X-Requested-With," + AUTH_HEADER);
        responseHeaders.add("Access-Control-Allow-Credentials", "true");
        responseHeaders.add("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS,HEAD");
        responseHeaders.add("Access-Control-Max-Age", "1209600");
    }
}
