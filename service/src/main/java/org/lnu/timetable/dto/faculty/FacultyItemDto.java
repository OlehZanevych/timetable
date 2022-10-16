package org.lnu.timetable.dto.faculty;

import lombok.Data;

@Data
public class FacultyItemDto {

    private Long id;

    private String name;

    private String website;

    private String email;

    private String phone;

    private String address;

    private String facultyLogoUri;
}
