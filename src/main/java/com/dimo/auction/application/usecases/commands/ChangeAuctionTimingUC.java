package com.dimo.auction.application.usecases.commands;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.shared.Id;

public interface ChangeAuctionTimingUC {

    Auction updateTiming(Id auctionId, AuctionTiming timing);
}
