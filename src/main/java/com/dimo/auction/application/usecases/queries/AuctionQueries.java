package com.dimo.auction.application.usecases.queries;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.shared.Id;

import java.util.List;

public interface AuctionQueries {
    List<Auction> allAuctions();

    List<Auction> allAccountAuctions(Id userId);

    List<Auction> allLiveAuctions();

    List<Auction> allNonStartedAuctions();
}
