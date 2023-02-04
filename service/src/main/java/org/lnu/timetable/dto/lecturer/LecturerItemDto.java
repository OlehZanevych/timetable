package org.lnu.timetable.dto.lecturer;

import lombok.Data;
import org.lnu.timetable.entity.scientific.degree.ScientificDegreeEntity;

@Data
public class LecturerItemDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String surname;
    private String email;
    private String phone;
    private ScientificDegreeEntity scientificDegree;
}
