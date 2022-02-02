package com.dimo.auction.application.usecases.queries;

import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.shared.Id;

import java.util.List;

public interface BidQueries {
    List<Bid> allBidsByAuctionId(Id auctionId);

    List<Bid> allBidsByAccountId(Id accountId);
}
