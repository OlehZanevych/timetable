package org.lnu.timetable.service.department;

import org.lnu.timetable.model.department.Department;
import org.lnu.timetable.model.faculty.Faculty;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties);
}
