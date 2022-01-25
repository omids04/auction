package com.dimo.auction.application.usecases;

import com.dimo.auction.application.usecases.model.BidCreateModel;

public interface PlaceBidUC {

    void placeBid(BidCreateModel model);
}
