package org.lnu.timetable.repository.department.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.department.Department;
import org.lnu.timetable.repository.department.DepartmentRepository;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.lnu.timetable.constants.ModelConstants.ID;
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
    public Mono<Department> create(Department department) {
        return r2dbcEntityTemplate.insert(department);
    }

    @Override
    public Flux<Department> findAll(Collection<String> fields, int limit, long offset) {
        Query query = empty().columns(fields).limit(limit)
                .sort(by(asc("name")));

        if (offset != 0) {
            query = query.offset(offset);
        }

        return r2dbcEntityTemplate.select(Department.class)
                .matching(query)
                .all();
    }

    @Override
    public Mono<Department> findById(Long id, Collection<String> fields) {
        return r2dbcEntityTemplate.select(Department.class)
                .matching(query(where(ID).is(id)).columns(fields)).one();
    }

    @Override
    public Mono<Long> count() {
        return r2dbcEntityTemplate.count(empty(), Department.class);
    }

    @Override
    public Mono<Boolean> update(Department department) {
        return r2dbcEntityTemplate.update(department)
                .map(updatedDepartment -> true)
                .onErrorReturn(TransientDataAccessResourceException.class, false);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return r2dbcEntityTemplate.delete(Department.class)
                .matching(query(Criteria.where(ID).is(id))).all()
                .map(affectedRows -> affectedRows > 0);
    }

    @Override
    public Flux<Department> findByFacultyIds(Collection<Long> facultyIds, Collection<String> fields) {
        return r2dbcEntityTemplate.select(Department.class)
            .matching(query(where("facultyId").in(facultyIds))
                .columns(fields)
                .sort(by(asc("name")))).all();
    }
}
