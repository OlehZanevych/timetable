package org.lnu.timetable.controller.faculty.queries;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.AllArgsConstructor;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.service.faculty.FacultyService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@SchemaMapping(typeName="FacultyQueries")
public class FacultyQueriesController {
    private final FacultyService facultyService;

    @SchemaMapping
    public Flux<Faculty> facultyConnection(DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        return facultyService.findAll(fs, context);
    }

    @SchemaMapping
    public Mono<Faculty> faculty(@Argument Long id, DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        return facultyService.findById(id, fs, context);
    }
}
