package org.lnu.timetable.service.auth.impl;

import graphql.GraphQLContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.auth.error.status.SignInErrorStatus;
import org.lnu.timetable.entity.common.response.MutationResponse;
import org.lnu.timetable.entity.user.credentials.UserCredentials;
import org.lnu.timetable.repository.user.UserRepository;
import org.lnu.timetable.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.lnu.timetable.constants.GraphQlContextConstants.USER_ID;
import static org.lnu.timetable.entity.common.response.MutationResponse.errorMutationResponse;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String USER_DATA_CLAIMS = "userData";
    private static final String AUTH_ISSUER = "Auto Service Auth";

    private static final MutationResponse<SignInErrorStatus> INVALID_CREDENTIALS_SIGN_IN_MUTATION_RESPONSE =
            errorMutationResponse(SignInErrorStatus.INVALID_CREDENTIALS);

    private static final MutationResponse<SignInErrorStatus> INTERNAL_SERVER_ERROR_SIGN_IN_MUTATION_RESPONSE =
            errorMutationResponse(SignInErrorStatus.INTERNAL_SERVER_ERROR);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final long authTokenLifetime;

    private final String jwtSigningKey;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${AUTH_TOKEN_LIFETIME}") long authTokenLifetime,
                           @Value("${JWT_SIGNING_KEY}") String jwtSigningKey) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authTokenLifetime = authTokenLifetime;
        this.jwtSigningKey = jwtSigningKey;
    }

    @Override
    public Mono<MutationResponse<SignInErrorStatus>> signIn(UserCredentials userCredentials, GraphQLContext context) {
        String username = userCredentials.getUsername();

        return userRepository.findAuthData(username).map(user -> {
            String password = userCredentials.getPassword();
            String passwordHash = user.getPasswordHash();
            if (!passwordEncoder.matches(password, passwordHash)) {
                return INVALID_CREDENTIALS_SIGN_IN_MUTATION_RESPONSE;
            }

            context.put(USER_ID, user.getId());
            return MutationResponse.<SignInErrorStatus>successfulMutationResponse();
        }).onErrorResume(e -> Mono.just(INTERNAL_SERVER_ERROR_SIGN_IN_MUTATION_RESPONSE));
    }

    private String generateToken(Long userId) {
        Claims claims = Jwts.claims();
        UserData userData = new UserData(userId);
        claims.put(USER_DATA_CLAIMS, userData);

        long creationTime = System.currentTimeMillis();
        long expirationTime = creationTime + authTokenLifetime;

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(AUTH_ISSUER)
                .setIssuedAt(new Date(creationTime))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey)
                .compact();
    }

    private record UserData(Long userId) {
    }
}
