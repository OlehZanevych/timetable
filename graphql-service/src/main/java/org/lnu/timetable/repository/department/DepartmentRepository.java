package org.lnu.timetable.repository.department;

import org.lnu.timetable.model.department.Department;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface DepartmentRepository {
    Flux<Department> findByFacultyIds(Collection<Long> facultyIds, Collection<String> fields);
}
