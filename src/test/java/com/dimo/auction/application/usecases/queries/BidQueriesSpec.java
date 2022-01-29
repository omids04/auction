package com.dimo.auction.application.usecases.queries;

import com.dimo.auction.application.ports.input.queries.BidQueriesInputPort;
import com.dimo.auction.application.ports.output.BidOutputPort;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.Bid;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import io.cucumber.java8.En;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BidQueriesSpec implements En {

    BidQueries queries;
    BidOutputPort outputPort;

    Id auctionId;
    Id accountId;
    List<Bid> bids;
    int count;
    public BidQueriesSpec() {
        outputPort = Mockito.mock(BidOutputPort.class);
        queries = new BidQueriesInputPort(outputPort);
        Given("auction with id {string}", (String auctionId) -> {
            this.auctionId = Id.of(UUID.fromString(auctionId));
            var bid1 = new Bid(Id.generate(), Price.of(BigInteger.ONE));
            var bid2 = new Bid(Id.generate(), Price.of(BigInteger.TWO));
            var bid3 = new Bid(Id.generate(), Price.of(BigInteger.TEN));
            var bid4 = new Bid(Id.generate(), Price.of(BigInteger.TEN));
            this.count = 4;
            when(outputPort.getByAuctionId(this.auctionId)).thenReturn(List.of(bid1, bid2, bid3, bid4));
        });
        When("user request to see all bids that belong to given auction", () -> {
            this.bids = queries.allBidsByAuctionId(this.auctionId);
        });

        Given("an account with id {string}", (String accountId) -> {
            this.accountId = Id.of(UUID.fromString(accountId));
            var bid1 = new Bid(this.accountId, Price.of(BigInteger.ONE));
            var bid2 = new Bid(this.accountId, Price.of(BigInteger.TWO));
            var bid3 = new Bid(this.accountId, Price.of(BigInteger.TEN));
            this.count = 3;
            when(outputPort.getByAccountId(this.auctionId)).thenReturn(List.of(bid1, bid2, bid3));
        });
        When("when users wants to see all his bids", () -> {
            this.bids = queries.allBidsByAccountId(this.auctionId);
        });
        Then("he should be given a list of all bids", () -> {
            assertEquals(this.count, bids.size());

        });
    }
}
