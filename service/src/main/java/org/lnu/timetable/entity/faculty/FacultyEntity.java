package org.lnu.timetable.entity.faculty;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("faculties")
public class FacultyEntity {
    @Id
    private Long id;

    private String name;

    private String website;

    private String email;

    private String phone;

    private String address;

    private String info;
}
