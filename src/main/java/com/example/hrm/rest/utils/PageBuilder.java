package com.example.hrm.rest.utils;

import com.example.hrm.dto.RoomSearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Component
public final class PageBuilder {
    private PageBuilder() {
    }

    public static PageRequest of(RoomSearchCriteria criteria) {
        Sort sort = by(
                Direction.fromString(criteria.getSortDirection()),
                criteria.getSortBy()
        );
        return PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(), sort);
    }
}
