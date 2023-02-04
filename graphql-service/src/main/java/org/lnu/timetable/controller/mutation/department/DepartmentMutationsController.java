package org.lnu.timetable.controller.mutation.department;

import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.common.CreateMutationResponse;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.entity.department.error.status.DepartmentCreateErrorStatus;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@SchemaMapping(typeName="DepartmentMutations")
public class DepartmentMutationsController {
    private final DepartmentService departmentService;

    @SchemaMapping
    public Mono<CreateMutationResponse<Department, DepartmentCreateErrorStatus>> createDepartment(@Argument Department department, DataFetchingFieldSelectionSet fs) {
        return departmentService.create(department, fs);
    }
}
