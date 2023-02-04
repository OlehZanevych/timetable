package org.lnu.timetable.entity.department;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class DepartmentViewEntity extends DepartmentEntity {
    private String facultyName;
}
