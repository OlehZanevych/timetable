package org.lnu.timetable.service.department.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.dto.department.DepartmentDto;
import org.lnu.timetable.dto.department.DepartmentViewDto;
import org.lnu.timetable.entity.department.DepartmentViewEntity;
import org.lnu.timetable.entity.lecturer.LecturerEntity;
import org.lnu.timetable.mapper.department.DepartmentMapper;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.lnu.timetable.repository.lecturer.LecturerRepository;
import org.lnu.timetable.service.department.DepartmentService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private LecturerRepository lecturerRepository;

    private DepartmentMapper departmentMapper;

    @Override
    public Mono<DepartmentDto> findById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(departmentMapper::toDto);
    }

    @Override
    public Mono<DepartmentViewDto> findDepartmentViewById(Long departmentId) {
        Mono<DepartmentViewEntity> departmentViewMono = departmentRepository.findDepartmentViewById(departmentId);
        Flux<LecturerEntity> lecturersFlux = lecturerRepository.findItemsByDepartmentId(departmentId);

        return Mono.zip(departmentViewMono, lecturersFlux.collectList()).map(tuple -> {
            DepartmentViewEntity departmentViewEntity = tuple.getT1();
            List<LecturerEntity> lecturerEntities = tuple.getT2();
            System.out.println(lecturerEntities);

            DepartmentViewDto departmentViewDto = departmentMapper.toDepartmentViewDto(departmentViewEntity);
            return departmentViewDto;
        });
    }
}
