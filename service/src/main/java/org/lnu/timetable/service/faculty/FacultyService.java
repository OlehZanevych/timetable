package org.lnu.timetable.service.faculty;

import org.lnu.timetable.dto.faculty.FacultyContentDto;
import org.lnu.timetable.dto.faculty.FacultyDto;
import org.lnu.timetable.dto.faculty.FacultyItemDto;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FacultyService {

    Mono<FacultyDto> create(FacultyContentDto facultyContentDto);

    Flux<FacultyItemDto> findAllItems();

    Mono<FacultyDto> findById(Long facultyId);

    Mono<Void> update(Long facultyId, FacultyContentDto facultyContentDto);

    Mono<Void> remove(Long facultyId);

    Mono<Void> uploadFacultyLogo(Long facultyId, FilePart facultyLogoFilePart);

    Flux<DataBuffer> readFacultyLogo(Long facultyId);
}
