package org.lnu.timetable.service.faculty;

import graphql.schema.DataFetchingFieldSelectionSet;
import org.lnu.timetable.model.faculty.Faculty;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacultyService {
    Flux<Faculty> findAll(DataFetchingFieldSelectionSet fs);
    Mono<Faculty> findById(Long id, DataFetchingFieldSelectionSet fs);
    Mono<String> findLogoUri(Faculty faculty);
    Flux<DataBuffer> readFacultyLogo(Long facultyId);
}
