package org.lnu.timetable.mapper.faculty;

import org.lnu.timetable.dto.faculty.FacultyContentDto;
import org.lnu.timetable.dto.faculty.FacultyDto;
import org.lnu.timetable.dto.faculty.FacultyItemDto;
import org.lnu.timetable.entity.faculty.FacultyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyEntity toFaculty(FacultyContentDto facultyContentDto);

    FacultyDto toFacultyDto(FacultyEntity faculty);

    List<FacultyItemDto> toFacultyItems(List<FacultyEntity> faculties);

}
