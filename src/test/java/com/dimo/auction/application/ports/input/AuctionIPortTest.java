package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionOPort;
import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.domain.auction.exceptions.AuctionNotWithinAllowedHoursException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionIPortTest {

    @Mock
    AuctionOPort auctionOPort;

    @InjectMocks
    AuctionIPort iPort;



    // Scenario: some user wants to add an action that does not start or end within allowed hours;
    @Test
    void auctionDoesNotStartWithinAllowedTimeScenario() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 20, 3, 15);
        AuctionCreateModel auction = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        //then
        assertThrows(AuctionNotWithinAllowedHoursException.class, () -> iPort.addAuction(auction));
    }

    // Scenario: a user wants to bid on a closed auction
    // app should not allow for that to happen with an error msg
    @Test
    void bidingOnAClosedAuctionScenario() {
        // given

        //when

        //then
    }

    // Scenario: when a user sends a request for placing a new bid on an item with lower price
    // than currentHighest bid price, request should get rejected with an error msg.
    @Test
    void bidingWithLowerPriceThanCurrentBidScenario(){

    }


    // Scenario: when a user place a new bid on an item then
    // current highest bid price should get updated
    @Test
    void placingNewBidScenario(){

    }
}