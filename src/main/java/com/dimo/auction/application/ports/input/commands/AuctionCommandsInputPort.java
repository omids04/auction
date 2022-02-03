package com.dimo.auction.application.ports.input.commands;

import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.ports.output.CurrentTimeOutputPort;
import com.dimo.auction.application.usecases.commands.AuctionCreateUC;

import com.dimo.auction.application.usecases.commands.BidingUC;
import com.dimo.auction.application.usecases.commands.ChangeAuctionTimingUC;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.auction.operations.AuctionOperations;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuctionCommandsInputPort implements AuctionCreateUC, BidingUC, ChangeAuctionTimingUC {

    private final AuctionOutputPort auctionOP;
    private final CurrentTimeOutputPort currentTimeOutputPort;

    @Override
    public Auction create(Id itemId, Id accountId, Price basePrice,AuctionTiming timing) {
        var auction = AuctionOperations.build(itemId, accountId, basePrice, timing);
        auctionOP.save(auction);
        return auction;
    }

    @Override
    public Bid placeBid(Id auctionId, Id accountId, Price price) {
        var auction = auctionOP.getById(auctionId);
        auction.bid(accountId, price, currentTimeOutputPort.currentTime());
        auctionOP.save(auction);
        return auction.getBids().highestBid().orElse(null);
    }

    @Override
    public Auction updateTiming(Id auctionId, AuctionTiming timing) {
        var auction = auctionOP.getById(auctionId);
        auction.updateTiming(timing, currentTimeOutputPort.currentTime());
        auctionOP.save(auction);
        return auction;
    }
}
