package com.dimo.auction.application.ports.output;

import com.dimo.auction.domain.auction.vos.Bid;
import com.dimo.auction.domain.shared.Id;

import java.util.List;

public interface BidOutputPort {
    List<Bid> getByAuctionId(Id auctionId);

    List<Bid> getByAccountId(Id auctionId);
}
