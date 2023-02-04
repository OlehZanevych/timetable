package org.lnu.timetable.mapper.department;

import org.lnu.timetable.dto.department.DepartmentDto;
import org.lnu.timetable.dto.department.DepartmentItemDto;
import org.lnu.timetable.dto.department.DepartmentViewDto;
import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.department.DepartmentViewEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto toDto(DepartmentEntity departmentEntity);

    DepartmentViewDto toDepartmentViewDto(DepartmentViewEntity departmentEntity);

    List<DepartmentItemDto> toDepartmentItemDtoList(List<DepartmentEntity> department);

}
