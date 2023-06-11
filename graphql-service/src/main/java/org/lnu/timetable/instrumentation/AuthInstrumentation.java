package org.lnu.timetable.instrumentation;

import graphql.execution.instrumentation.ExecutionStrategyInstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionStrategyParameters;
import org.springframework.stereotype.Component;

@Component
public class AuthInstrumentation extends SimpleInstrumentation {
    @Override
    public ExecutionStrategyInstrumentationContext beginExecutionStrategy(InstrumentationExecutionStrategyParameters parameters) {
        return super.beginExecutionStrategy(parameters);
    }
}
