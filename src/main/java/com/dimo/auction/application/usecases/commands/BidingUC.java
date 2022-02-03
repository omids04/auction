package com.dimo.auction.application.usecases.commands;

import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

public interface BidingUC {
    Bid placeBid(Id auctionId, Id accountId, Price price);
}
