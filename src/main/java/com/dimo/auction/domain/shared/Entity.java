package com.dimo.auction.domain.shared;

import lombok.Getter;

@Getter
public abstract class Entity<ID> {
    protected ID id;

    public Entity(ID id) {
        ValidationUtil
                .getValidator()
                .notNull(id, "Id can't be null");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity<?> entity = (Entity<?>) o;

        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
