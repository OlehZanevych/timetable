package org.lnu.timetable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private Long id;

    private String login;

    private String passwordHash;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String info;
}
