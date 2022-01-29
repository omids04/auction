package com.dimo.auction.application.ports.output;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.shared.Id;

import java.util.List;

public interface AuctionOutputPort {
    void save(Auction auction);

    Auction getById(Id id);

    List<Auction> getAll();

    List<Auction> getByAccountId(Id userId);

}

