package org.lnu.timetable.service.department;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.lnu.timetable.entity.common.response.CreateMutationResponse;
import org.lnu.timetable.entity.common.response.MutationResponse;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.entity.department.error.status.DepartmentCreateErrorStatus;
import org.lnu.timetable.entity.department.error.status.DepartmentDeleteErrorStatus;
import org.lnu.timetable.entity.department.error.status.DepartmentUpdateErrorStatus;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.service.common.CommonEntityService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends CommonEntityService<Department> {
    Mono<CreateMutationResponse<Department, DepartmentCreateErrorStatus>> create(Department Department, DataFetchingFieldSelectionSet fs);
    Mono<MutationResponse<DepartmentUpdateErrorStatus>> update(Long id, Department department);
    Mono<MutationResponse<DepartmentDeleteErrorStatus>> delete(Long id);
    Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties, GraphQLContext context);
}
