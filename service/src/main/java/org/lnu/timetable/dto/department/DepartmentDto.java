package org.lnu.timetable.dto.department;

import lombok.Data;

@Data
public class DepartmentDto {

    private Long id;

    private String name;

    private Long facultyId;

    private String email;

    private String phone;

    private String info;

}
