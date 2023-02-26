package org.lnu.timetable.controller.query.department;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.common.response.Connection;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@SchemaMapping(typeName="DepartmentQueries")
public class DepartmentQueriesController {
    private final DepartmentService departmentService;

    @SchemaMapping
    public Mono<Connection<Department>> departmentConnection(@Argument int limit, @Argument long offset,
                                                             DataFetchingFieldSelectionSet fs, GraphQLContext context) {

        return departmentService.getConnection(fs, limit, offset, context);
    }

    @SchemaMapping
    public Mono<Department> department(@Argument Long id, DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        return departmentService.findById(id, fs, context);
    }
}
