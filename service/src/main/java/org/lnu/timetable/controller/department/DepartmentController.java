package org.lnu.timetable.controller.department;

import lombok.AllArgsConstructor;
import org.lnu.timetable.dto.department.DepartmentDto;
import org.lnu.timetable.dto.department.DepartmentViewDto;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping("{departmentId}")
    public Mono<DepartmentDto> findById(@PathVariable Long departmentId) {
        return departmentService.findById(departmentId);
    }

    @GetMapping("{departmentId}/view")
    public Mono<DepartmentViewDto> findDepartmentViewById(@PathVariable Long departmentId) {
        return departmentService.findDepartmentViewById(departmentId);
    }
}
