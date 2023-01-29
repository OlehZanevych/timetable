package org.lnu.timetable.service.department.impl;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import lombok.AllArgsConstructor;
import org.lnu.timetable.constants.GraphQlSchemaConstants;
import org.lnu.timetable.entity.common.MutationResponse;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.entity.department.input.DepartmentInputErrorStatus;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.lnu.timetable.service.common.impl.CommonEntityServiceImpl;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.lnu.timetable.constants.GraphQlContextConstants.DEPARTMENT_SELECTED_DB_FIELDS;
import static org.lnu.timetable.entity.common.MutationResponse.createErrorMutationResponse;
import static org.lnu.timetable.entity.common.MutationResponse.createSuccessMutationResponse;
import static org.lnu.timetable.util.FieldSelectionUtil.getSelectedDbFields;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends CommonEntityServiceImpl<Department> implements DepartmentService {

    private static final String FACULTY_ID = "facultyId";

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    @Override
    public Mono<MutationResponse<Department, DepartmentInputErrorStatus>> create(Department department, DataFetchingFieldSelectionSet fs) {
        return departmentRepository.create(department)
                .flatMap(createdDepartment -> {
                    List<String> selectedFacultyDbFields = getFacultySelectedDbFieldsInResponseData(fs);
                    if (selectedFacultyDbFields != null) {
                        return facultyRepository.findById(createdDepartment.getFacultyId(), selectedFacultyDbFields).map(faculty -> {
                            createdDepartment.setFaculty(faculty);
                            return createDepartmentResponse(createdDepartment);
                        });
                    }

                    return Mono.just(createDepartmentResponse(createdDepartment));
                }
              )
                .onErrorResume(e -> {
                    DepartmentInputErrorStatus errorStatus = DepartmentInputErrorStatus.INTERNAL_SERVER_ERROR;
                    if (e instanceof DuplicateKeyException
                            && "duplicate key value violates unique constraint \"departments_name_key\"".equals(e.getCause().getMessage())) {

                        errorStatus = DepartmentInputErrorStatus.DUPLICATED_NAME;
                    } else if (e instanceof DataIntegrityViolationException &&
                            "insert or update on table \"departments\" violates foreign key constraint \"departments_faculty_id_fkey\"".equals(e.getCause().getMessage())) {

                        errorStatus = DepartmentInputErrorStatus.FACULTY_NOT_FOUND;
                    }

                    return Mono.just(createErrorMutationResponse(errorStatus));
                });
    }

    @Override
    protected Flux<Department> findAll(Collection<String> fields, int limit, long offset) {
        return departmentRepository.findAll(fields, limit, offset);
    }

    @Override
    public Mono<Department> findById(Long id, DataFetchingFieldSelectionSet fs, GraphQLContext context) {
        List<String> selectedDbFields = getSelectedDbFields(Department.selectableDbFields, fs);

        return departmentRepository.findById(id, selectedDbFields);
    }

    @Override
    protected Mono<Long> count() {
        return departmentRepository.count();
    }

    @Override
    protected void processNodesFieldSelection(DataFetchingFieldSelectionSet nodesFs, GraphQLContext context) {
    }

    @Override
    public Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties, GraphQLContext context) {
        Set<Long> facultyIds = faculties.stream().map(Faculty::getId).collect(toSet());

        Set<String> selectedDbFields = context.get(DEPARTMENT_SELECTED_DB_FIELDS);
        selectedDbFields.add(FACULTY_ID);

        return departmentRepository.findByFacultyIds(facultyIds, selectedDbFields).collectList().map(departments -> {
            Map<Long, List<Department>> facultyIdDepartmentMap = departments.stream().collect(groupingBy(Department::getFacultyId));

            return faculties.stream().collect(Collectors.toMap(Function.identity(), faculty -> {
                List<Department> facultyDepartments = facultyIdDepartmentMap.get(faculty.getId());
                return facultyDepartments == null ? List.of() : facultyDepartments;
            }));
        });
    }

    private List<String> getFacultySelectedDbFieldsInResponseData(DataFetchingFieldSelectionSet fs) {
        List<SelectedField> dataFieldSearchResult = fs.getFields(GraphQlSchemaConstants.DATA);
        if (dataFieldSearchResult.size() == 1) {
            DataFetchingFieldSelectionSet dataFs = dataFieldSearchResult.get(0).getSelectionSet();
            List<SelectedField> facultyFieldSearchResult = dataFs.getFields(GraphQlSchemaConstants.FACULTY);
            if (facultyFieldSearchResult.size() == 1) {
                return getFacultySelectedDbFields(facultyFieldSearchResult.get(0).getSelectionSet());
            }
        }

        return null;
    }

    private List<String> getFacultySelectedDbFields(DataFetchingFieldSelectionSet facultyFs) {
        return getSelectedDbFields(Faculty.selectableDbFields, facultyFs);
    }

    private MutationResponse<Department, DepartmentInputErrorStatus> createDepartmentResponse(Department department) {
        return createSuccessMutationResponse(department);
    }
}
