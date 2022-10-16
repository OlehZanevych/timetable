package org.lnu.timetable.repository.user;

import org.lnu.timetable.entity.user.UserEntity;
import org.lnu.timetable.entity.user.UserUpdateEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<UserEntity> create(UserEntity UserEntity);

    Flux<UserEntity> findAllItems();

    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> findById(Long UserId);

    Mono<Void> update(UserUpdateEntity UserEntity);

    Mono<Void> remove(Long UserId);
}