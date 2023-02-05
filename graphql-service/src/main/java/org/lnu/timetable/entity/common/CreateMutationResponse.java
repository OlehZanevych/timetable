package org.lnu.timetable.entity.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateMutationResponse<Entity, ErrorStatus> {
    public static <Entity, ErrorStatus> CreateMutationResponse<Entity, ErrorStatus> successfulCreateMutationResponse(Entity data) {
        return new CreateMutationResponse<>(true, data, null);
    }

    public static <Entity, ErrorStatus> CreateMutationResponse<Entity, ErrorStatus> errorCreateMutationResponse(ErrorStatus errorStatus) {
        return new CreateMutationResponse<>(false, null, errorStatus);
    }

    private final boolean isSuccess;
    private final Entity data;
    private final ErrorStatus errorStatus;

}
