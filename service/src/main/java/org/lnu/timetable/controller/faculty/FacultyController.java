package org.lnu.timetable.controller.faculty;

import lombok.AllArgsConstructor;
import org.lnu.timetable.dto.faculty.FacultyContentDto;
import org.lnu.timetable.dto.faculty.FacultyDto;
import org.lnu.timetable.dto.faculty.FacultyItemDto;
import org.lnu.timetable.service.faculty.FacultyService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.lnu.timetable.constants.Constants.IMAGE_FILE_REQUEST_PARAM;

@RestController
@AllArgsConstructor
@RequestMapping(FacultyController.FACULTIES_ROOT_URI)
public class FacultyController {

    public static final String FACULTIES_ROOT_URI = "faculties";

    public static final String FACULTY_LOGO_SUB_URI = "logo";

    private final FacultyService facultyService;

    @PostMapping
    public Mono<FacultyDto> create(@RequestBody FacultyContentDto facultyContentDto) {
        return facultyService.create(facultyContentDto);
    }

    @GetMapping("items")
    public Flux<FacultyItemDto> findAllItems() {
        return facultyService.findAllItems();
    }

    @GetMapping("{facultyId}")
    public Mono<FacultyDto> find(@PathVariable Long facultyId) {
        return facultyService.findById(facultyId);
    }

    @PutMapping("{facultyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable Long facultyId, @RequestBody FacultyContentDto facultyContentDto) {
        return facultyService.update(facultyId, facultyContentDto);
    }

    @DeleteMapping("{facultyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> remove(@PathVariable Long facultyId) {
        return facultyService.remove(facultyId);
    }

    @PostMapping(value = "{facultyId}/" + FACULTY_LOGO_SUB_URI, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> uploadFacultyLogo(@PathVariable Long facultyId,
                                        @RequestPart(IMAGE_FILE_REQUEST_PARAM) FilePart facultyLogoFilePart) {

        return facultyService.uploadFacultyLogo(facultyId, facultyLogoFilePart);
    }

    @GetMapping(value = "{facultyId}/" + FACULTY_LOGO_SUB_URI, produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE
    })
    public Flux<DataBuffer> readFacultyLogo(@PathVariable Long facultyId) {
        return facultyService.readFacultyLogo(facultyId);
    }
}
