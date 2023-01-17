package org.lnu.timetable.entity.faculty;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("faculties")
public class Faculty {
    public static final List<String> selectableDbFields = List.of(
        "name",
        "website",
        "email",
        "phone",
        "address",
        "info"
    );

    @Id
    private Long id;

    private String name;

    private String website;

    private String email;

    private String phone;

    private String address;

    private String info;
}
