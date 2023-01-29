package org.lnu.timetable.entity.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MutationResponse<Entity, ErrorStatus> {
    public static <Entity, ErrorStatus> MutationResponse<Entity, ErrorStatus> createSuccessMutationResponse(Entity data) {
        return new MutationResponse<>(true, data, null);
    }

    public static <Entity, ErrorStatus> MutationResponse<Entity, ErrorStatus> createErrorMutationResponse(ErrorStatus errorStatus) {
        return new MutationResponse<>(false, null, errorStatus);
    }

    private final boolean isSuccess;
    private final Entity data;
    private final ErrorStatus errorStatus;
}
