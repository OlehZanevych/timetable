package org.lnu.timetable.entity.department;

import java.util.stream.Stream;

public enum DepartmentColumn {
    id,
    name,
    facultyId,
    email,
    phone,
    info;

    public static final String[] ALL_COLUMN_NAMES = Stream.of(DepartmentColumn.values())
            .map(v -> v.name()).toArray(String[]::new);
}