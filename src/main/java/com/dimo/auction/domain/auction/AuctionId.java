package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.shared.ValueObject;

import java.util.UUID;

public class AuctionId extends ValueObject {

    private final UUID auctionId;

    public AuctionId() {
        auctionId = UUID.randomUUID();
    }

    public UUID getId(){
        return this.auctionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionId auctionId1 = (AuctionId) o;

        return auctionId.equals(auctionId1.auctionId);
    }

    @Override
    public int hashCode() {
        return auctionId.hashCode();
    }
}
