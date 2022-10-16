package org.lnu.timetable.dto.user;

import lombok.Data;

@Data
public class UserCreateDto extends UserUpdateDto {
    private String username;
    private String password;
}
