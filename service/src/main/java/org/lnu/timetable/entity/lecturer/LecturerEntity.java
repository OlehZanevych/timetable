package org.lnu.timetable.entity.lecturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.scientific.degree.ScientificDegreeEntity;
import org.lnu.timetable.entity.user.UserEntity;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("lecturers")
public class LecturerEntity extends UserEntity {
    private DepartmentEntity department;
    private ScientificDegreeEntity scientificDegree;
}
