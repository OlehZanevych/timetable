package org.lnu.timetable.repository.faculty;

import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.entity.faculty.field.selection.FacultyFieldSelection;
import org.springframework.graphql.data.GraphQlRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GraphQlRepository
public interface FacultyRepository {
    Mono<Faculty> create(Faculty faculty);
    Flux<Faculty> findAll(FacultyFieldSelection fieldSelection, int limit, long offset);
    Mono<Faculty> findById(Long id, FacultyFieldSelection fieldSelection);
    Mono<Long> count();
    Mono<Boolean> update(Faculty faculty);
    Mono<Boolean> delete(Long id);
}
