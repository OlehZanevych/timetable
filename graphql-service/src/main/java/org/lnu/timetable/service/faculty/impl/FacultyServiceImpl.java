package org.lnu.timetable.service.faculty.impl;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import org.lnu.timetable.model.department.Department;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.lnu.timetable.service.faculty.FacultyService;
import org.lnu.timetable.service.file.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

import static org.lnu.timetable.constants.ApiConstants.FACULTIES_ROOT_URI;
import static org.lnu.timetable.constants.ApiConstants.FACULTY_LOGO_SUB_URI;
import static org.lnu.timetable.constants.GraphQlContextConstants.DEPARTMENT_SELECTED_DB_FIELDS;
import static org.lnu.timetable.util.FieldSelectionUtil.getSelectedDbFieldSet;
import static org.lnu.timetable.util.FieldSelectionUtil.getSelectedDbFields;

@Service
public class FacultyServiceImpl implements FacultyService {

    private static final String FACULTY_LOGO_FOLDER = "faculties/logos/";

    private static final String DEPARTMENTS = "departments";

    private final FacultyRepository facultyRepository;

    private final FileStorageService fileStorageService;

    private final String serviceHost;

    public FacultyServiceImpl(FacultyRepository facultyRepository, FileStorageService fileStorageService,
                              @Value("${service.host}") String serviceHost) {

        this.facultyRepository = facultyRepository;
        this.fileStorageService = fileStorageService;
        this.serviceHost = serviceHost;
    }

    @Override
    public Flux<Faculty> findAll(DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        List<String> selectedDbFields = getSelectedDbFields(Faculty.selectableDbFields, fs);
        saveDepartmentSelectedDbFieldsIntoContext(fs, context);

        return facultyRepository.findAll(selectedDbFields);
    }

    @Override
    public Mono<Faculty> findById(Long id, DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        List<String> selectedDbFields = getSelectedDbFields(Faculty.selectableDbFields, fs);
        saveDepartmentSelectedDbFieldsIntoContext(fs, context);

        return facultyRepository.findById(id, selectedDbFields);
    }

    @Override
    public Mono<String> findLogoUri(Faculty faculty) {
        Long facultyId = faculty.getId();
        String logoFileName = getFacultyLogoFileName(facultyId);

        return fileStorageService.checkIfFileExists(logoFileName).flatMap(logoExists -> logoExists
            ? Mono.just(serviceHost + FACULTIES_ROOT_URI + "/" + faculty.getId() + "/" + FACULTY_LOGO_SUB_URI)
            : Mono.empty());
    }

    @Override
    public Flux<DataBuffer> readFacultyLogo(Long facultyId) {
        String facultyLogoFileName = getFacultyLogoFileName(facultyId);
        return fileStorageService.readFile(facultyLogoFileName);
    }

    private void saveDepartmentSelectedDbFieldsIntoContext(DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        List<SelectedField> departmentsFieldSearchResult = fs.getFields(DEPARTMENTS);
        if (departmentsFieldSearchResult.size() == 1) {
            Set<String> currentDepartmentSelectedDbFields = getSelectedDbFieldSet(Department.selectableDbFields,
                departmentsFieldSearchResult.get(0).getSelectionSet());

            Set<String> departmentSelectedDbFields = context.get(DEPARTMENT_SELECTED_DB_FIELDS);
            if (departmentSelectedDbFields == null) {
                context.put(DEPARTMENT_SELECTED_DB_FIELDS, currentDepartmentSelectedDbFields);
            } else {
                departmentSelectedDbFields.addAll(currentDepartmentSelectedDbFields);
            }
        }
    }

    private String getFacultyLogoFileName(Long facultyId) {
        return FACULTY_LOGO_FOLDER + facultyId;
    }
}
