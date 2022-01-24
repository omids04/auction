package com.dimo.auction.application.usecases;

import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.application.usecases.model.AuctionDetailModel;

public interface AuctionCreateUC {
    AuctionDetailModel addAuction(AuctionCreateModel auction);
}
