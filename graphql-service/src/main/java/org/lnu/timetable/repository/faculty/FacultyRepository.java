package org.lnu.timetable.repository.faculty;

import org.lnu.timetable.model.faculty.Faculty;
import org.springframework.graphql.data.GraphQlRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@GraphQlRepository
public interface FacultyRepository {
    Flux<Faculty> findAll(Collection<String> fields);
    Mono<Faculty> findById(Long id, Collection<String> fields);
}
