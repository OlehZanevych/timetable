package org.lnu.timetable.service.department.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.model.department.Department;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.lnu.timetable.constants.ModelConstants.ID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Mono<Map<Faculty, List<Department>>> findForFaculties(List<Faculty> faculties) {
        Set<Long> facultyIds = faculties.stream().map(Faculty::getId).collect(toSet());

        List<String> fields = new ArrayList<>();
        fields.add(ID);
        fields.addAll(Department.selectableFields);

        return departmentRepository.findByFacultyIds(facultyIds, fields).collectList().map(departments -> {
            Map<Long, List<Department>> facultyIdDepartmentMap = departments.stream().collect(groupingBy(Department::getFacultyId));

            return faculties.stream().collect(Collectors.toMap(Function.identity(), faculty -> {
                List<Department> facultyDepartments = facultyIdDepartmentMap.get(faculty.getId());
                return facultyDepartments == null ? List.of() : facultyDepartments;
            }));
        });
    }
}
