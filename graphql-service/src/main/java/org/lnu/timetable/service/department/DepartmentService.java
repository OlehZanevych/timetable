package org.lnu.timetable.service.department;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.lnu.timetable.entity.common.MutationResponse;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.entity.department.input.DepartmentInputErrorStatus;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.service.common.CommonEntityService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends CommonEntityService<Department> {
    Mono<MutationResponse<Department, DepartmentInputErrorStatus>> create(Department Department, DataFetchingFieldSelectionSet fs);
    Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties, GraphQLContext context);
}
