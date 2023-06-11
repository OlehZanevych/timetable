package org.lnu.timetable.service.auth;

import graphql.GraphQLContext;
import org.lnu.timetable.entity.auth.error.status.SignInErrorStatus;
import org.lnu.timetable.entity.common.response.MutationResponse;
import org.lnu.timetable.entity.user.credentials.UserCredentials;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<MutationResponse<SignInErrorStatus>> signIn(UserCredentials userCredentials, GraphQLContext context);
}
