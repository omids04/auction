package com.dimo.auction.domain.aggregates.auction;

import java.util.UUID;

public class AuctionId {

    private final UUID auctionId;

    public AuctionId() {
        auctionId = UUID.randomUUID();
    }

    public UUID getId(){
        return this.auctionId;
    }
}
