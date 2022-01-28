package com.dimo.auction.application.usecases;

import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.usecases.commands.ChangeAuctionTimingUC;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.exceptions.TimingModificationException;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import io.cucumber.java8.En;
import io.cucumber.java8.Th;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChangeAuctionTimingUcSpec implements En {

    ChangeAuctionTimingUC uc;

    AuctionOutputPort outputPort;
    Id id;
    Exception exception;
    Auction updatedAuction;
    AuctionTiming newTiming;
    AuctionTiming previousTiming;


    public ChangeAuctionTimingUcSpec() {
        outputPort = Mockito.mock(AuctionOutputPort.class);

        Given("an auction with id {string}", (String auctionId) -> {
            id = Id.of(UUID.fromString(auctionId));
        });
        Given("start time tomorrow at noon and duration {int} minutes", (Integer duration) -> {
            previousTiming = AuctionTiming
                    .of(this::getTomorrowNoonTime, getTomorrowNoonTime().plusDays(1), Duration.ofMinutes(duration));
            when(outputPort.getById(id))
                    .thenReturn(new Auction(id, Id.generate(), Id.generate(), Price.of(BigInteger.ONE), previousTiming));
        });
        Given("a finished auction with id {string}", (String auctionId) -> {
            this.id = Id.of(UUID.fromString(auctionId));
            AuctionTiming timing = AuctionTiming
                    .of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusDays(1), Duration.ofMinutes(30));
            when(outputPort.getById(id))
                    .thenReturn(new Auction(id, Id.generate(), Id.generate(), Price.of(BigInteger.ONE), timing));
        });
        Given("a live auction with id {string}", (String auctionId) -> {
            this.id = Id.of(UUID.fromString(auctionId));
        });
        Given("duration of {int} minutes", (Integer duration) -> {
            previousTiming = AuctionTiming
                    .of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(10), Duration.ofMinutes(duration));
            when(outputPort.getById(id))
                    .thenReturn(new Auction(id, Id.generate(), Id.generate(), Price.of(BigInteger.ONE), previousTiming));
        });
        When("user wants to change start time to day after tomorrow and duration to {int} minutes",
                (Integer duration) -> {
                     this.newTiming = AuctionTiming
                            .of(this::getTomorrowNoonTime,
                                    previousTiming.getStartTime(),
                                    Duration.ofMinutes(duration));
            try {
                updatedAuction = uc.updateTiming(id, newTiming);
            }catch (Exception e){
                this.exception = e;
            }
        });
        When("user wants to change duration to {int} minutes", (Integer duration) -> {
            this.newTiming = AuctionTiming
                    .of(this::getTomorrowNoonTime,
                            getTomorrowNoonTime().plusDays(2),
                            Duration.ofMinutes(duration));
            try {
                updatedAuction = uc.updateTiming(id, newTiming);
            }catch (Exception e){
                this.exception = e;
            }
        });

        When("user wants to change start time", () -> {
            this.newTiming = AuctionTiming
                    .of(this::getTomorrowNoonTime,
                            previousTiming.getStartTime().minusMinutes(50),
                            previousTiming.getDuration());
            try {
                updatedAuction = uc.updateTiming(id, newTiming);
            }catch (Exception e){
                this.exception = e;
            }
        });

        Then("he should be able to do so", () -> {
            assertEquals(newTiming, updatedAuction.getTiming());
            verify(outputPort, times(1)).save(any());
            verify(outputPort, times(1)).getById(any());
            assertNull(exception);
        });
        Then("he should not be able to do so", () -> {
            assertNotNull(exception);
            assertTrue(exception instanceof TimingModificationException);
            verify(outputPort, times(0)).save(any());
            verify(outputPort, times(1)).getById(any());
        });
    }

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }
}
