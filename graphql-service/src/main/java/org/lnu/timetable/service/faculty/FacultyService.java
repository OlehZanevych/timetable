package org.lnu.timetable.service.faculty;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.service.common.CommonEntityService;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacultyService extends CommonEntityService<Faculty> {
    Mono<String> findLogoUri(Faculty faculty);
    Flux<DataBuffer> readFacultyLogo(Long facultyId);
}
