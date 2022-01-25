package com.dimo.auction.application.ports.output;

import com.dimo.auction.domain.auction.Auction;

public interface AuctionCreateOutPort {
    void save(Auction auction);
}
