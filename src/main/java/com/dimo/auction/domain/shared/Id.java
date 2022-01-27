package com.dimo.auction.domain.shared;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Id extends ValueObject {

    private UUID id;

    private Id() {
    }

    public static Id of(UUID uuid){
        Id id = new Id();
        id.setId(uuid);
        return id;
    }

    public static Id generate(){
        Id id = new Id();
        id.setId(UUID.randomUUID());
        return id;
    }

    private void setId(UUID id){
        if(id == null)
            throw new IllegalArgumentException("id can't be null");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id auctionId1 = (Id) o;

        return id.equals(auctionId1.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
