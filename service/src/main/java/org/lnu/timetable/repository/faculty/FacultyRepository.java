package org.lnu.timetable.repository.faculty;

import org.lnu.timetable.entity.faculty.FacultyEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacultyRepository {

    Mono<FacultyEntity> create(FacultyEntity facultyEntity);

    Flux<FacultyEntity> findAllItems();

    Mono<FacultyEntity> findById(Long facultyId);

    Mono<Void> update(FacultyEntity facultyEntity);

    Mono<Void> remove(Long facultyId);
}
