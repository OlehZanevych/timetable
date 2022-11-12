package org.lnu.timetable.repository.department.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.model.department.Department;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.empty;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@AllArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<Department> findByFacultyIds(Collection<Long> facultyIds, Collection<String> fields) {
        return r2dbcEntityTemplate.select(Department.class)
            .matching(query(where("facultyId").in(facultyIds))
                .columns(fields)
                .sort(by(asc("name")))).all();
    }
}
