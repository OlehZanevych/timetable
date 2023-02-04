package org.lnu.timetable.dto.faculty;

import lombok.Data;
import org.lnu.timetable.dto.department.DepartmentItemDto;

import java.util.List;

@Data
public class FacultyDto extends FacultyContentDto {

    private Long id;

    private String facultyLogoUri;

    private List<DepartmentItemDto> departments;
}
