package org.lnu.timetable.controller.query;

import lombok.AllArgsConstructor;
import org.lnu.timetable.entity.common.graphql.schema.GraphQlSchemaDefinition;
import org.lnu.timetable.entity.query.departments.DepartmentQueries;
import org.lnu.timetable.entity.query.faculties.FacultyQueries;
import org.lnu.timetable.service.graphgl.schema.GraphQlSchemaService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class QueryController {

    private final GraphQlSchemaService graphQlSchemaService;

    @QueryMapping
    public GraphQlSchemaDefinition _service() {
        return graphQlSchemaService.getSchemaDefinition();
    }
    @QueryMapping
    public FacultyQueries faculties() {
        return new FacultyQueries();
    }
    @QueryMapping
    public DepartmentQueries departments()  {
        return new DepartmentQueries();
    }
}
