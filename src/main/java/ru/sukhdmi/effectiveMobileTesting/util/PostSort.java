package ru.sukhdmi.effectiveMobileTesting.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum PostSort {

    TIMESTAMP_ASC(Sort.by(Sort.Direction.ASC, "timestamp")),

    TIMESTAMP_DESC(Sort.by(Sort.Direction.DESC, "timestamp"));

    private final Sort sortValue;
}
