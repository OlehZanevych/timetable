package org.lnu.timetable.service.department;

import graphql.GraphQLContext;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.service.common.CommonEntityService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface DepartmentService extends CommonEntityService<Department> {
    Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties, GraphQLContext context);
}
