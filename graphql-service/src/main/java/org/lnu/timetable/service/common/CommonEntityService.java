package org.lnu.timetable.service.common;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.lnu.timetable.entity.common.response.Connection;
import reactor.core.publisher.Mono;

public interface CommonEntityService<Entity> {
    Mono<Connection<Entity>> getConnection(DataFetchingFieldSelectionSet fs, int limit, long offset, GraphQLContext context);
    Mono<Entity> findById(Long id, DataFetchingFieldSelectionSet fs, GraphQLContext context);
}
