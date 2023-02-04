package org.lnu.timetable.entity.scientific.degree;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("scientific_degrees")
public class ScientificDegreeEntity {
    @Id
    private Long id;

    private String name;

    public ScientificDegreeEntity(Long id) {
        this.id = id;
    }
}
