package org.lnu.timetable.service.department;

import org.lnu.timetable.dto.department.DepartmentDto;
import org.lnu.timetable.dto.department.DepartmentViewDto;
import reactor.core.publisher.Mono;

public interface DepartmentService {

    Mono<DepartmentDto> findById(Long departmentId);

    Mono<DepartmentViewDto> findDepartmentViewById(Long departmentId);

}
