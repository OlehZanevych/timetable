package org.lnu.timetable.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfo {
    private final long total;
    private final boolean hasNextPage;
    private final Long nextPageOffset;

    public PageInfo(long total) {
        this.total = total;

        hasNextPage = false;
        nextPageOffset = null;
    }

    public PageInfo(long total, long nextPageOffset) {
        this.total = total;

        hasNextPage = nextPageOffset > total;

        this.nextPageOffset = hasNextPage ? nextPageOffset : null;
    }
}
