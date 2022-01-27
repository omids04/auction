package com.dimo.auction.application.usecases.commands;

import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

public interface BidingUC {
    void placeBid(Id auctionId, Id accountId, Price price);
}
