package org.lnu.timetable.controller.mutation;

import org.lnu.timetable.entity.mutation.department.DepartmentMutations;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationController {
    @MutationMapping
    public DepartmentMutations departments()  {
        return new DepartmentMutations();
    }
}
