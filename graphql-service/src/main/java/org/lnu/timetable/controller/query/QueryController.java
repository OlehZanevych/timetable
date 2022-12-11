package org.lnu.timetable.controller.query;

import org.lnu.timetable.model.faculty.FacultyQueries;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryController {
    @QueryMapping
    public FacultyQueries faculties() {
        return new FacultyQueries();
    }
}
