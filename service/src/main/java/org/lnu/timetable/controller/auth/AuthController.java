package org.lnu.timetable.controller.auth;

import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.lnu.timetable.annotation.auth.Auth;
import org.lnu.timetable.dto.user.credentials.UserCredentials;
import org.lnu.timetable.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.lnu.timetable.constants.Constants.AUTH_HEADER;

@RestController
@AllArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("sign-in")
    public Mono<ResponseEntity> logIn(@Valid @RequestBody UserCredentials userCredentials) {
        return authService.logIn(userCredentials).map(authToken -> ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(AUTH_HEADER, authToken)
                .build());
    }

    @Auth
    @GetMapping("refresh-token")
    public Mono<ResponseEntity> refreshToken() {
        // Refreshing of Auth token occurs in CommonWebFilter. Now it only works with ResponseEntity
        // TODO Try to get rid of ResponseEntity
        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }
}

