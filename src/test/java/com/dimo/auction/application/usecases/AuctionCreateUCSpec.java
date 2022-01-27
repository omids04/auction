package com.dimo.auction.application.usecases;

import com.dimo.auction.application.ports.input.AuctionInputPort;
import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.usecases.commands.AuctionCreateUC;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.exceptions.AuctionTimingException;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import io.cucumber.java8.En;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuctionCreateUCSpec implements En {

    Id itemId;
    Id accountId;
    Duration duration;
    Price basePrice;
    LocalDateTime startTime;
    Auction auction;
    AuctionCreateUC uc;

    @Mock
    AuctionOutputPort auctionOutputPort;

    public AuctionCreateUCSpec() {
        auctionOutputPort = Mockito.mock(AuctionOutputPort.class);
        uc = new AuctionInputPort(auctionOutputPort);

        Given("item with id {string}", (String itemId) -> {
            this.itemId = Id.of(UUID.fromString(itemId));
        });

        Given("account with id {string}", (String accountId) -> {
            this.accountId = Id.of(UUID.fromString(accountId));
        });

        Given("duration {int} minutes", (Integer duration) -> {
            this.duration = Duration.ofMinutes(duration);
        });

        Given("base price {string}", (String price) -> {
            this.basePrice = Price.of(new BigInteger(price));
        });

        Given("start time tomorrow at noon", () -> {
            startTime = this.getTomorrowNoonTime();
        });

        Given("start time 22PM at night$", () -> {
            this.startTime = getTomorrowNoonTime().plusHours(10);
        });

        When("user want to create a new auction", () -> {
            var timing = AuctionTiming
                    .of(this::getTomorrowNoonTime, this.startTime, this.duration);
            this.auction = uc.create(itemId, accountId, basePrice, timing);
        });

        Then("he should be able to do so", () -> {
            assertNotNull(this.auction.getId());
            verify(auctionOutputPort, times(1)).save(any());
        });

        Then("he's request should be denied with an error message", () -> {
            var timing = AuctionTiming
                    .of(this::getTomorrowNoonTime, this.startTime, this.duration);
            assertThrows(AuctionTimingException.class,() -> uc.create(itemId, accountId, basePrice, timing));
            verify(auctionOutputPort, times(0)).save(any());

        });

    }


    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }
}
