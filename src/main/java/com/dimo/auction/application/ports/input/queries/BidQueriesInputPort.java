package com.dimo.auction.application.ports.input.queries;

import com.dimo.auction.application.ports.output.BidOutputPort;
import com.dimo.auction.application.usecases.queries.BidQueries;
import com.dimo.auction.domain.auction.vos.Bid;
import com.dimo.auction.domain.shared.Id;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BidQueriesInputPort implements BidQueries {
    private final BidOutputPort outputPort;

    @Override
    public List<Bid> allBidsByAuctionId(Id auctionId) {
        return outputPort.getByAuctionId(auctionId);
    }

    @Override
    public List<Bid> allBidsByAccountId(Id accountId) {
        return outputPort.getByAccountId(accountId);
    }
}
