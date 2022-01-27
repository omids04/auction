package com.dimo.auction.application.usecases.commands;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

public interface AuctionCreateUC {
    Auction create(Id itemId, Id accountId, Price basePrice, AuctionTiming timing);
}
