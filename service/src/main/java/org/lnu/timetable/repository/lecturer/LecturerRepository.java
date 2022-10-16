package org.lnu.timetable.repository.lecturer;

import org.lnu.timetable.entity.lecturer.LecturerEntity;
import reactor.core.publisher.Flux;

public interface LecturerRepository {
    Flux<LecturerEntity> findItemsByDepartmentId(Long departmentId);
}
