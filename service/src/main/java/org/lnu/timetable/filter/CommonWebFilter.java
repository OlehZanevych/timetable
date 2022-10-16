package org.lnu.timetable.filter;

import lombok.AllArgsConstructor;
import org.lnu.timetable.dto.user.jwt.JwtUser;
import org.lnu.timetable.service.auth.AuthService;
import org.lnu.timetable.service.jwt.JwtTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;

import static org.lnu.timetable.constants.Constants.AUTH_HEADER;
import static org.lnu.timetable.constants.Constants.USER_CTX_KEY;

@Component
@AllArgsConstructor
public class CommonWebFilter implements WebFilter {

    private final AuthService authService;

    private final JwtTokenService jwtTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        HttpHeaders responseHeaders = response.getHeaders();

        addCorsHeaders(responseHeaders);

        response.beforeCommit(() -> addRefreshedAuthTokenToResponse(responseHeaders));

        return webFilterChain.filter(serverWebExchange)
                .contextWrite(ctx -> authenticateUserAndAddToContext(serverWebExchange, ctx));
    }

    private Context authenticateUserAndAddToContext(ServerWebExchange serverWebExchange, Context ctx) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        List<String> authHeaderValues = request.getHeaders().get(AUTH_HEADER);
        if (authHeaderValues == null || authHeaderValues.size() != 1) {
            return ctx;
        }

        return authService.authenticateUserAndAddToContext(ctx, authHeaderValues.get(0));
    }

    // TODO Explore whether it is possible to use something better than Mono.deferContextual
    private Mono<Void> addRefreshedAuthTokenToResponse(HttpHeaders responseHeaders) {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey(USER_CTX_KEY)) {
                JwtUser user = ctx.get(USER_CTX_KEY);
                responseHeaders.add("Access-Control-Expose-Headers", AUTH_HEADER);
                responseHeaders.add(AUTH_HEADER, jwtTokenService.generateToken(user));
            }

            return Mono.just(ctx);
        }).then();
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
