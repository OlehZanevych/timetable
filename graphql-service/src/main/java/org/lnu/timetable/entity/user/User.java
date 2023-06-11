package org.lnu.timetable.entity.user;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(User.TABLE)
public class User {

    public static final String TABLE = "users";

    private Long id;
    private String username;
    private String passwordHash;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private String info;
}
