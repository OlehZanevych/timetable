package org.lnu.timetable.repository.department.impl;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.department.DepartmentColumn;
import org.lnu.timetable.entity.department.DepartmentEntity;
import org.lnu.timetable.entity.department.DepartmentViewEntity;
import org.lnu.timetable.exception.NotFoundException;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.BiFunction;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@AllArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final String SELECT_DEPARTMENT_VIEW_QUERY = """
            SELECT
                d.id,
                d.name,
                d.faculty_id,
                f.name faculty_name,
                d.email,
                d.phone,
                d.info
            FROM departments d
            JOIN faculties f ON (f.id = d.faculty_id)
            WHERE d.id = $1
                        """;

    public static final BiFunction<Row, RowMetadata, DepartmentViewEntity> DEPARTMENT_VIEW_ROW_MAPPER =
            (row, rowMetaData) -> DepartmentViewEntity.builder()
                    .id(row.get("id", Long.class))
                    .name(row.get("name", String.class))
                    .facultyId(row.get("faculty_id", Long.class))
                    .facultyName(row.get("faculty_name", String.class))
                    .email(row.get("email", String.class))
                    .phone(row.get("phone", String.class))
                    .info(row.get("info", String.class))
                    .build();

    private final NotFoundException NOT_FOUND_EXCEPTION = new NotFoundException("Department not found!");

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<DepartmentEntity> findItemsByFacultyId(Long facultyId) {
        return r2dbcEntityTemplate.select(DepartmentEntity.class)
                .matching(query(
                        where(DepartmentColumn.facultyId.name()).is(facultyId)
                ).columns(
                        DepartmentColumn.id.name(),
                        DepartmentColumn.name.name(),
                        DepartmentColumn.email.name(),
                        DepartmentColumn.phone.name()
                ).sort(by(asc(DepartmentColumn.name.name())))).all();
    }

    @Override
    public Mono<DepartmentEntity> findById(Long departmentId) {
        return r2dbcEntityTemplate.select(DepartmentEntity.class)
                .matching(query(
                        where(DepartmentColumn.id.name()).is(departmentId)
                ).columns(
                        DepartmentColumn.ALL_COLUMN_NAMES
                )).first()
                .switchIfEmpty(Mono.error(NOT_FOUND_EXCEPTION));
    }

    @Override
    public Mono<DepartmentViewEntity> findDepartmentViewById(Long departmentId) {
        return r2dbcEntityTemplate.getDatabaseClient().sql(SELECT_DEPARTMENT_VIEW_QUERY)
                .bind(0, departmentId)
                .map(DEPARTMENT_VIEW_ROW_MAPPER).one()
                .switchIfEmpty(Mono.error(NOT_FOUND_EXCEPTION));
    }
}
