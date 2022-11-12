package org.lnu.timetable.controller.faculty;

import lombok.AllArgsConstructor;
import org.lnu.timetable.model.department.Department;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.service.department.DepartmentService;
import org.lnu.timetable.service.faculty.FacultyService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.lnu.timetable.constants.ApiConstants.FACULTIES_ROOT_URI;
import static org.lnu.timetable.constants.ApiConstants.FACULTY_LOGO_SUB_URI;

@Controller
@AllArgsConstructor
@SchemaMapping(typeName="Faculty")
@RequestMapping(FACULTIES_ROOT_URI)
public class FacultyController {
    private final FacultyService facultyService;
    private final DepartmentService departmentService;

    @BatchMapping
    public Mono<Map<Faculty, List<Department>>> departments(List<Faculty> faculties) {
        return departmentService.findForFaculties(faculties);
    }

    @SchemaMapping
    public Mono<String> logoUri(Faculty faculty) {
        return facultyService.findLogoUri(faculty);
    }

    @ResponseBody
    @GetMapping(value = "{facultyId}/" + FACULTY_LOGO_SUB_URI, produces = {
        MediaType.IMAGE_PNG_VALUE,
        MediaType.IMAGE_JPEG_VALUE,
        MediaType.IMAGE_GIF_VALUE
    })
    public Flux<DataBuffer> findLogo(@PathVariable Long facultyId) {
        return facultyService.readFacultyLogo(facultyId);
    }
}
