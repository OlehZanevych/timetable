package org.lnu.timetable.service.faculty.impl;

import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.AllArgsConstructor;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.lnu.timetable.service.faculty.FacultyService;
import org.lnu.timetable.service.file.storage.FileStorageService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.lnu.timetable.constants.ApiConstants.FACULTIES_ROOT_URI;
import static org.lnu.timetable.constants.ApiConstants.FACULTY_LOGO_SUB_URI;
import static org.lnu.timetable.constants.ModelConstants.ID;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private static final String FACULTY_LOGO_FOLDER = "faculties/logos/";

    private final FacultyRepository facultyRepository;

    private final FileStorageService fileStorageService;

    @Override
    public Flux<Faculty> findAll(DataFetchingFieldSelectionSet fs) {
        List<String> fields = new ArrayList<>();
        fields.add(ID);
        Faculty.selectableFields.forEach(declaredField -> {
            if (fs.contains(declaredField)) {
                fields.add(declaredField);
            }
        });

        return facultyRepository.findAll(fields);
    }

    @Override
    public Mono<Faculty> findById(Long id, DataFetchingFieldSelectionSet fs) {
        List<String> fields = new ArrayList<>();
        fields.add(ID);
        Faculty.selectableFields.forEach(declaredField -> {
            if (fs.contains(declaredField)) {
                fields.add(declaredField);
            }
        });

        return facultyRepository.findById(id, fields);
    }

    @Override
    public Mono<String> findLogoUri(Faculty faculty) {
        Long facultyId = faculty.getId();
        String logoFileName = getFacultyLogoFileName(facultyId);

        return fileStorageService.checkIfFileExists(logoFileName).flatMap(logoExists ->
            logoExists ? Mono.just(FACULTIES_ROOT_URI + "/" + faculty.getId() + "/" + FACULTY_LOGO_SUB_URI)
                : Mono.empty());
    }

    @Override
    public Flux<DataBuffer> readFacultyLogo(Long facultyId) {
        String facultyLogoFileName = getFacultyLogoFileName(facultyId);
        return fileStorageService.readFile(facultyLogoFileName);
    }

    private String getFacultyLogoFileName(Long facultyId) {
        return FACULTY_LOGO_FOLDER + facultyId;
    }
}
