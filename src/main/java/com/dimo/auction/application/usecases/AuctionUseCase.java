package com.dimo.auction.application.usecases;

import com.dimo.auction.application.usecases.model.AuctionCreateModel;

public interface AuctionUseCase {
    void addAuction(AuctionCreateModel auction);
}
