package org.lnu.timetable.entity.department;

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
@Table("departments")
public class DepartmentEntity {

    @Id
    private Long id;

    private String name;

    private Long facultyId;

    private String email;

    private String phone;

    private String info;

    public DepartmentEntity(Long id) {
        this.id = id;
    }
}
