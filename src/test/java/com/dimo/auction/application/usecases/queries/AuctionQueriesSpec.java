package com.dimo.auction.application.usecases.queries;

import com.dimo.auction.application.ports.input.queries.AuctionQueriesInputPort;
import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.operations.AuctionOperations;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import io.cucumber.java8.En;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuctionQueriesSpec implements En {

    AuctionQueries queries;
    AuctionOutputPort outputPort;
    List<Auction> auctions;
    Id userId;
    public AuctionQueriesSpec() {
        outputPort = Mockito.mock(AuctionOutputPort.class);
        queries = new AuctionQueriesInputPort(outputPort);
        loadInitData();

        When("user request to see all auctions", () -> {
            auctions = queries.allAuctions();
        });
        Then("he should be given a list of all auctions", () -> {
            assertEquals(5, auctions.size());
        });
        When("user request to see all live auctions", () -> {
            auctions = queries.allLiveAuctions();
        });
        Then("he should be given a list of all live auctions", () -> {
            assertEquals(2, auctions.size());
        });
        When("user request to see all not started yet auctions", () -> {
            auctions = queries.allNonStartedAuctions();
        });
        Then("he should be given a list of all not started yet auctions", () -> {
            assertEquals(2, auctions.size());
        });
        Given("a user with id {string}", (String userId) -> {
            this.userId = Id.of(UUID.fromString(userId));
            var auction1 = AuctionOperations
                    .build(Id.generate(), Id.generate(), Price.of(BigInteger.ONE),
                            AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().plusDays(5), Duration.ofMinutes(30)));
            var auction2 = AuctionOperations
                    .build(Id.generate(), Id.generate(), Price.of(BigInteger.TWO),
                            AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(50)));
            when(outputPort.getByAccountId(this.userId)).thenReturn(List.of(auction1, auction2));
        });
        When("user request to see all his auctions", () -> {
            auctions = queries.allAccountAuctions(this.userId);
        });
        Then("he should be given a list of all auctions that belong to his account", () -> {
            assertEquals(2, auctions.size());
        });
    }

    private void loadInitData() {
        var auction1 = AuctionOperations
                .build(Id.generate(), Id.generate(), Price.of(BigInteger.ONE),
                        AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().plusDays(5), Duration.ofMinutes(30)));
        var auction2 = AuctionOperations
                .build(Id.generate(), Id.generate(), Price.of(BigInteger.TWO),
                        AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(50)));
        var auction3 = AuctionOperations
                .build(Id.generate(), Id.generate(), Price.of(BigInteger.TEN),
                        AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().plusDays(1), Duration.ofMinutes(300)));
        var auction4 = AuctionOperations
                .build(Id.generate(), Id.generate(), Price.of(BigInteger.ONE),
                        AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(10), Duration.ofMinutes(30)));
        var auction5 = AuctionOperations
                .build(Id.generate(), Id.generate(), Price.of(BigInteger.TWO),
                        AuctionTiming.of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusNanos(1), Duration.ofMinutes(30)));
        when(outputPort.getAll()).thenReturn(List.of(auction1, auction2, auction3, auction4, auction5));
    }

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }
}
