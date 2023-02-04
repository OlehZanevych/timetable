package org.lnu.timetable.repository.department;

import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.department.DepartmentViewEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentRepository {

    Flux<DepartmentEntity> findItemsByFacultyId(Long facultyId);

    Mono<DepartmentEntity> findById(Long departmentId);

    Mono<DepartmentViewEntity> findDepartmentViewById(Long departmentId);

}
