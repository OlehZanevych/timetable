package org.lnu.timetable.repository.department;

import org.lnu.timetable.entity.department.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface DepartmentRepository {
    Flux<Department> findAll(Collection<String> fields, int limit, long offset);
    Mono<Department> findById(Long id, Collection<String> fields);
    Mono<Long> count();
    Flux<Department> findByFacultyIds(Collection<Long> facultyIds, Collection<String> fields);
}
