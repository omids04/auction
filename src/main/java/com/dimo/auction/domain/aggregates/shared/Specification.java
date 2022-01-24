package com.dimo.auction.domain.aggregates.shared;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);
    Specification<T> and(Specification<T> specification);
}
