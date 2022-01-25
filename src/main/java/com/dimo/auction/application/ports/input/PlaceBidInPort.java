package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.PlaceBidOutPort;
import com.dimo.auction.application.usecases.PlaceBidUC;
import com.dimo.auction.application.usecases.model.BidCreateModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceBidInPort implements PlaceBidUC {

    private final PlaceBidOutPort placeBidOutPort;
    @Override
    public void placeBid(BidCreateModel model) {

    }
}
