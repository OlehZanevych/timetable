package org.lnu.timetable.dto.faculty;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FacultyContentDto {
    @NotNull
    private String name;

    @NotNull
    private String website;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private String info;
}
