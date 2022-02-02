package com.dimo.auction.application.ports.input.queries;

import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.ports.output.CurrentTimeOutputPort;
import com.dimo.auction.application.usecases.queries.AuctionQueries;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.operations.AuctionOperations;
import com.dimo.auction.domain.shared.Id;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuctionQueriesInputPort implements AuctionQueries {
    private final AuctionOutputPort outputPort;
    private final CurrentTimeOutputPort currentTimeOutputPort;


    @Override
    public List<Auction> allAuctions() {
        return outputPort.getAll();
    }

    @Override
    public List<Auction> allAccountAuctions(Id accountId) {
        return outputPort.getByAccountId(accountId);
    }

    @Override
    public List<Auction> allLiveAuctions() {
        return outputPort
                .getAll()
                .stream()
                .filter(AuctionOperations.liveFilter(currentTimeOutputPort.currentTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Auction> allNonStartedAuctions() {
        return outputPort
                .getAll()
                .stream()
                .filter(AuctionOperations.nonStartedFilter(currentTimeOutputPort.currentTime()))
                .collect(Collectors.toList());
    }
}
