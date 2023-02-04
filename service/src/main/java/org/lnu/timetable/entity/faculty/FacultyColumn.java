package org.lnu.timetable.entity.faculty;

import java.util.stream.Stream;

public enum FacultyColumn {
    id,
    name,
    website,
    email,
    phone,
    address,
    info;

    public static final String[] ALL_COLUMN_NAMES = Stream.of(FacultyColumn.values())
            .map(v -> v.name()).toArray(String[]::new);
}
