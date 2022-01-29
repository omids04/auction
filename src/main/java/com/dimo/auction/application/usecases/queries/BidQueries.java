package com.dimo.auction.application.usecases.queries;

import com.dimo.auction.domain.auction.Auction;

import java.util.List;

public interface BidQueries {
    List<Auction> allBidsByAuctionId(List<Auction> auctions);

    List<Auction> allBidsByAccountId(List<Auction> auctions);
}
