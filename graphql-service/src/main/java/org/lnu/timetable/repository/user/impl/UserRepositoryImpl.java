package org.lnu.timetable.repository.user.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.user.User;
import org.lnu.timetable.repository.user.UserRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.lnu.timetable.constants.ModelConstants.ID;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<User> findAuthData(String username) {
        return r2dbcEntityTemplate.select(User.class)
                .matching(query(where("username").is(username)).columns(ID, "passwordHash")).one();
    };
}
