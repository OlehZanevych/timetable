package org.lnu.timetable.util;

import org.lnu.timetable.entity.common.response.Connection;

import java.util.List;

public class ConnectionUtil {
    public static <T> Connection<T> createConnectionResponse(List<T> nodes) {
        return new Connection<>(nodes);
    }
}
