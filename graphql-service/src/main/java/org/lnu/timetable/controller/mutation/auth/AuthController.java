package org.lnu.timetable.controller.mutation.auth;

import graphql.GraphQLContext;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.auth.error.status.SignInErrorStatus;
import org.lnu.timetable.entity.common.response.MutationResponse;
import org.lnu.timetable.entity.user.credentials.UserCredentials;
import org.lnu.timetable.service.auth.AuthService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@SchemaMapping(typeName="AuthMutations")
public class AuthController {

    private final AuthService authService;

    @SchemaMapping
    public Mono<MutationResponse<SignInErrorStatus>> signIn(@Argument UserCredentials credentials, GraphQLContext context) {
        return authService.signIn(credentials, context);
    }
}
