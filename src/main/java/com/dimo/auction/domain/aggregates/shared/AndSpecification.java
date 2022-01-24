package com.dimo.auction.domain.aggregates.shared;

public class AndSpecification<T> extends AbstractSpecification<T> {
    private final Specification<T> firstSpec;
    private final Specification<T> secondSpec;

    public AndSpecification(Specification<T> firstSpec, Specification<T> secondSpec) {
        this.firstSpec = firstSpec;
        this.secondSpec = secondSpec;
    }

    @Override
    public boolean isSatisfiedBy(T t) {
        return firstSpec.isSatisfiedBy(t) && secondSpec.isSatisfiedBy(t);
    }
}
