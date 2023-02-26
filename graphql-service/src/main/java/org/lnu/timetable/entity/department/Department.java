package org.lnu.timetable.entity.department;

import lombok.Data;
import org.lnu.timetable.entity.faculty.Faculty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table(Department.TABLE)
public class Department {

    public static final String TABLE = "departments";

    public static final List<String> selectableDbFields = List.of(
        "name",
        "email",
        "phone",
        "info"
    );

    @Id
    private Long id;

    private String name;

    private Long facultyId;

    private Faculty faculty;

    private String email;

    private String phone;

    private String info;

}
