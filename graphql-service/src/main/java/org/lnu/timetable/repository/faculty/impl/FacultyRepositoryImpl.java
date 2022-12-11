package org.lnu.timetable.repository.faculty.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.model.faculty.Faculty;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
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
public class FacultyRepositoryImpl implements FacultyRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<Faculty> findAll(Collection<String> fields) {
        return r2dbcEntityTemplate.select(Faculty.class)
            .matching(empty().columns(fields)
                .sort(by(asc("name")))).all();
    }

    @Override
    public Mono<Faculty> findById(Long id, Collection<String> fields) {
        return r2dbcEntityTemplate.select(Faculty.class)
            .matching(query(where(ID).is(id)).columns(fields)).one();
    }
}
