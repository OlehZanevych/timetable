package org.lnu.timetable.model.department;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("departments")
public class Department {

    public static final List<String> selectableFields = List.of(
        "name",
        "facultyId",
        "email",
        "phone",
        "info"
    );

    @Id
    private Long id;

    private String name;

    private Long facultyId;

    private String email;

    private String phone;

    private String info;

}
