package com.dimo.auction.application.usecases.commands;

import com.dimo.auction.application.ports.input.commands.AuctionCommandsInputPort;
import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.domain.auction.Auction;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BidingUCSpec implements En {


    BidingUC uc;
    AuctionOutputPort outputPort;
    Id auctionId;
    Id accountId;
    Exception exception;

    public BidingUCSpec() {
        outputPort = Mockito.mock(AuctionOutputPort.class);
        uc = new AuctionCommandsInputPort(outputPort, this::getTomorrowNoonTime);
        Given("an open auction with id {string} and {int} as price of highest bid", (String uuid, Integer price) -> {
            auctionId = Id.of(UUID.fromString(uuid));
            Auction auction = new Auction(auctionId,
                    Id.generate(),
                    Id.generate(),
                    new Price(new BigInteger(Integer.toString(price))),
                    new AuctionTiming(getTomorrowNoonTime().minusMinutes(10), Duration.ofMinutes(20)));
            when(outputPort.getById(auctionId)).thenReturn(auction);
        });
        Given("a closed auction with id {string}", (String uuid) -> {
            auctionId = Id.of(UUID.fromString(uuid));
            Auction auction = new Auction(auctionId,
                    Id.generate(),
                    Id.generate(),
                    new Price(BigInteger.ONE),
                    new AuctionTiming(getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(20)));
            when(outputPort.getById(auctionId)).thenReturn(auction);
        });

        Given("^user account that have enough credit$", () -> {
        });
        When("^user wants to bid (\\d+) on that auction$", (Integer price) -> {
            this.accountId = Id.generate();
            try {
                uc.placeBid(auctionId, accountId, new Price(new BigInteger(Integer.toString(price))));
            }catch (Exception e){
                this.exception = e;
            }
        });
        Then("^he should be able to bid$", () -> {
            assertNull(exception);
        });
        Then("^he should not be able to bid$", () -> {
            assertNotNull(exception);
        });

    }

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }
}
