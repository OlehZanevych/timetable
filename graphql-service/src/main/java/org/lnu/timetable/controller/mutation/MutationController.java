package org.lnu.timetable.controller.mutation;

import org.lnu.timetable.entity.mutation.auth.AuthMutations;
import org.lnu.timetable.entity.mutation.departments.DepartmentMutations;
import org.lnu.timetable.entity.mutation.faculties.FacultyMutations;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationController {
    @MutationMapping
    public FacultyMutations faculties()  {
        return new FacultyMutations();
    }
    @MutationMapping
    public DepartmentMutations departments()  {
        return new DepartmentMutations();
    }
    @MutationMapping
    public AuthMutations auth()  {
        return new AuthMutations();
    }
}
