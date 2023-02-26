package org.lnu.timetable.entity.common.column;

import lombok.Data;

@Data
public class ColumnValue {
    private final String column;
    private final Object value;
}
