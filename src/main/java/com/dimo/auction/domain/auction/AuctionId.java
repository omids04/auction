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
}
