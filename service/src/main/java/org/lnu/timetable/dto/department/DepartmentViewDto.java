package org.lnu.timetable.dto.department;

import lombok.Data;
import org.lnu.timetable.dto.lecturer.LecturerItemDto;

import java.util.List;

@Data
public class DepartmentViewDto extends DepartmentDto {
    private String facultyName;
    private List<LecturerItemDto> lecturers;
}
