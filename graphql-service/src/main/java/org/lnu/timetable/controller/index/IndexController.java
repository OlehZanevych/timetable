package org.lnu.timetable.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

/**
 * Redirects to the GraphiQL IDE.
 */
@Controller
public class IndexController {

    @GetMapping
    public Rendering redirectToGraphiQlIde() {
        return Rendering.redirectTo("graphiql").build();
    }
}