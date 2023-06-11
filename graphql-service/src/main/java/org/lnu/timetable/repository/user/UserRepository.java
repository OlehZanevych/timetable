package org.lnu.timetable.repository.user;

import org.lnu.timetable.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findAuthData(String username);
}
