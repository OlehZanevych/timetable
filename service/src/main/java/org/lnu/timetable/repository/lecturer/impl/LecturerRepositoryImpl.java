package org.lnu.timetable.repository.lecturer.impl;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.department.DepartmentColumn;
import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.department.DepartmentViewEntity;
import org.lnu.timetable.entity.lecturer.LecturerColumn;
import org.lnu.timetable.entity.lecturer.LecturerEntity;
import org.lnu.timetable.entity.scientific.degree.ScientificDegreeEntity;
import org.lnu.timetable.entity.user.UserColumn;
import org.lnu.timetable.repository.lecturer.LecturerRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.BiFunction;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.empty;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@AllArgsConstructor
public class LecturerRepositoryImpl implements LecturerRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public static final BiFunction<Row, RowMetadata, LecturerEntity> LECTURER_VIEW_ROW_MAPPER =
            (row, rowMetaData) -> LecturerEntity.builder()
                    .id(row.get(UserColumn.id.name(), Long.class))
                    .firstName(row.get(UserColumn.firstName.name(), String.class))
                    .middleName(row.get(UserColumn.middleName.name(), String.class))
                    .lastName(row.get(UserColumn.lastName.name(), String.class))
                    .email(row.get(UserColumn.email.name(), String.class))
                    .phone(row.get(UserColumn.phone.name(), String.class))
                    .department(new DepartmentEntity(row.get(LecturerColumn.departmentId.name(), Long.class)))
                    .scientificDegree(new ScientificDegreeEntity(row.get(LecturerColumn.scientificDegreeId.name(), Long.class)))
                    .build();

    @PostConstruct
    private void init() {
        Long departmentId = 5L;
        try {
            List<LecturerEntity> lecturerEntities = r2dbcEntityTemplate.select(LecturerEntity.class)
                    .matching(empty().columns(
                            UserColumn.id.name(),
                            UserColumn.firstName.name(),
                            UserColumn.middleName.name(),
                            UserColumn.lastName.name(),
                            UserColumn.email.name(),
                            UserColumn.phone.name(),
                            "department.id",
                            "scientificDegree.id"
                    ))
                    .all().collectList().block();

            System.out.println(lecturerEntities);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    @Override
    public Flux<LecturerEntity> findItemsByDepartmentId(Long departmentId) {
        return r2dbcEntityTemplate.select(LecturerEntity.class)
                .matching(query(
                        where(LecturerColumn.departmentId.name()).is(departmentId)
                ).columns(
                        UserColumn.id.name(),
                        UserColumn.firstName.name(),
                        UserColumn.middleName.name(),
                        UserColumn.lastName.name(),
                        UserColumn.email.name(),
                        UserColumn.phone.name(),
                        LecturerColumn.departmentId.name(),
                        LecturerColumn.scientificDegreeId.name()
                ).sort(by(asc(UserColumn.firstName.name())))).all();
    }
}
