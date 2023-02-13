package org.lnu.timetable.service.faculty;

import org.lnu.timetable.entity.common.CreateMutationResponse;
import org.lnu.timetable.entity.common.MutationResponse;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.entity.faculty.error.status.FacultyCreateErrorStatus;
import org.lnu.timetable.entity.faculty.error.status.FacultyDeleteErrorStatus;
import org.lnu.timetable.entity.faculty.error.status.FacultyUpdateErrorStatus;
import org.lnu.timetable.service.common.CommonEntityService;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacultyService extends CommonEntityService<Faculty> {
    Mono<CreateMutationResponse<Faculty, FacultyCreateErrorStatus>> create(Faculty faculty);
    Mono<MutationResponse<FacultyUpdateErrorStatus>> update(Long id, Faculty faculty);
    Mono<MutationResponse<FacultyDeleteErrorStatus>> delete(Long id);
    Mono<String> findLogoUri(Faculty faculty);
    Flux<DataBuffer> readFacultyLogo(Long facultyId);
}
