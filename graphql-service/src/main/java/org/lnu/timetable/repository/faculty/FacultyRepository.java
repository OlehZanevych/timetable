package org.lnu.timetable.repository.faculty;

import org.lnu.timetable.entity.faculty.Faculty;
import org.springframework.graphql.data.GraphQlRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@GraphQlRepository
public interface FacultyRepository {
    Mono<Faculty> create(Faculty faculty);
    Flux<Faculty> findAll(Collection<String> fields, int limit, long offset);
    Mono<Faculty> findById(Long id, Collection<String> fields);
    Mono<Long> count();
    Mono<Boolean> update(Faculty faculty);
    Mono<Boolean> delete(Long id);
}
