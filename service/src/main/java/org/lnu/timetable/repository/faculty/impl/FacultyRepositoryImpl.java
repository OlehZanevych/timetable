package org.lnu.timetable.repository.faculty.impl;

import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.faculty.FacultyEntity;
import org.lnu.timetable.entity.faculty.FacultyColumn;
import org.lnu.timetable.exception.NotFoundException;
import org.lnu.timetable.repository.faculty.FacultyRepository;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Query.empty;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@AllArgsConstructor
public class FacultyRepositoryImpl implements FacultyRepository {

    private final NotFoundException NOT_FOUND_EXCEPTION = new NotFoundException("Faculty not found!");

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<FacultyEntity> create(FacultyEntity facultyEntity) {
        return r2dbcEntityTemplate.insert(facultyEntity);
    }

    @Override
    public Flux<FacultyEntity> findAllItems() {
        return r2dbcEntityTemplate.select(FacultyEntity.class)
                .matching(empty().columns(
                        FacultyColumn.id.name(),
                        FacultyColumn.name.name(),
                        FacultyColumn.website.name(),
                        FacultyColumn.email.name(),
                        FacultyColumn.address.name(),
                        FacultyColumn.phone.name()
                ).sort(by(asc(FacultyColumn.name.name())))).all();
    }

    @Override
    public Mono<FacultyEntity> findById(Long facultyId) {
        return r2dbcEntityTemplate.select(FacultyEntity.class)
                .matching(query(
                        Criteria.where(FacultyColumn.id.name()).is(facultyId)
                ).columns(
                        FacultyColumn.ALL_COLUMN_NAMES
                )).first()
                .switchIfEmpty(Mono.error(NOT_FOUND_EXCEPTION));
    }

    @Override
    public Mono<Void> update(FacultyEntity faculty) {
        return r2dbcEntityTemplate.update(faculty)
                .onErrorResume(TransientDataAccessResourceException.class, e -> Mono.error(NOT_FOUND_EXCEPTION))
                .then();
    }

    @Override
    public Mono<Void> remove(Long facultyId) {
        return r2dbcEntityTemplate.delete(FacultyEntity.class)
                .matching(query(
                        Criteria.where(FacultyColumn.id.name()).is(facultyId)
                )).all()
                .doOnSuccess(affectedRows -> {
                    if (affectedRows == 0) {
                        throw NOT_FOUND_EXCEPTION;
                    }
                }).then();
    }
}
