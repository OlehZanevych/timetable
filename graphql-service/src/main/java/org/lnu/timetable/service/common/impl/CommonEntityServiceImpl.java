package org.lnu.timetable.service.common.impl;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import org.lnu.timetable.entity.common.Connection;
import org.lnu.timetable.entity.common.PageInfo;
import org.lnu.timetable.entity.faculty.Faculty;
import org.lnu.timetable.service.common.CommonEntityService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

import static org.lnu.timetable.constants.GraphQlSchemaConstants.HAS_NEXT_PAGE;
import static org.lnu.timetable.constants.GraphQlSchemaConstants.NEXT_PAGE_CURSOR;
import static org.lnu.timetable.constants.GraphQlSchemaConstants.NODES;
import static org.lnu.timetable.constants.GraphQlSchemaConstants.PAGE_INFO;
import static org.lnu.timetable.util.FieldSelectionUtil.ID_FIELD_ONLY;
import static org.lnu.timetable.util.FieldSelectionUtil.getSelectedDbFields;

public abstract class CommonEntityServiceImpl<Entity> implements CommonEntityService<Entity> {
    @Override
    public Mono<Connection<Entity>> getConnection(DataFetchingFieldSelectionSet fs, int limit, long offset, GraphQLContext context) {
        List<SelectedField> nodesFieldSearchResult = fs.getFields(NODES);
        boolean isNodesFieldSelected = nodesFieldSearchResult.size() == 1;

        List<String> selectedDbFields;
        if (isNodesFieldSelected || checkIfCurrentPageInfoRequired(fs)) {
            if (isNodesFieldSelected) {
                DataFetchingFieldSelectionSet nodesFs = nodesFieldSearchResult.get(0).getSelectionSet();

                selectedDbFields = getSelectedDbFields(Faculty.selectableDbFields, nodesFs);

                processNodesFieldSelection(nodesFs, context);
            } else {
                selectedDbFields = ID_FIELD_ONLY;
            }

            return findAll(selectedDbFields, limit, offset).collectList().flatMap(nodes -> {
                if (fs.getFields(PAGE_INFO).size() == 1) {
                    if (nodes.size() < limit) {
                        long total = offset + nodes.size();
                        return Mono.just(new Connection<>(nodes, new PageInfo(total)));
                    }

                    return count().map(total -> {
                        long nextPageOffset = limit + offset;
                        return new Connection<>(nodes, new PageInfo(total, nextPageOffset));
                    });
                }

                return Mono.just(new Connection<>(nodes));
            });
        }

        return count().map(total -> {
            long nextPageOffset = limit + offset;
            return new Connection<>(new PageInfo(total, nextPageOffset));
        });
    }

    private boolean checkIfCurrentPageInfoRequired(DataFetchingFieldSelectionSet fs) {
        List<SelectedField> pageInfoFieldSearchResult = fs.getFields(PAGE_INFO);
        if (pageInfoFieldSearchResult.size() != 1) {
            return false;
        }

        DataFetchingFieldSelectionSet pageInfoFs = pageInfoFieldSearchResult.get(0).getSelectionSet();

        return pageInfoFs.contains(NEXT_PAGE_CURSOR) || pageInfoFs.contains(HAS_NEXT_PAGE);
    }

    protected abstract Flux<Entity> findAll(Collection<String> fields, int limit, long offset);

    protected abstract Mono<Long> count();

    protected abstract void processNodesFieldSelection(DataFetchingFieldSelectionSet nodesFs, GraphQLContext context);
}
