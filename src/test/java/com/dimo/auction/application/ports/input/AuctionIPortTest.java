package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionCreateOPort;
import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.application.usecases.model.AuctionDetailModel;
import com.dimo.auction.domain.auction.Auction;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// UseCase Main Scenario: user can create a new auction by providing an itemId, startTime, duration and price
// item with provided id should exist
// startTime must be in future
// duration should be more than 5 minutes
// auction can only start within allowed hours of day( between 8 AM and 18 PM)
// price should be positive
@ExtendWith(MockitoExtension.class)
class AuctionIPortTest {

    @Mock
    AuctionCreateOPort auctionOPort;

    @InjectMocks
    AuctionCreateIPort iPort;

    //Scenario: UseCase main scenario
    @Test
    void addingNewAuctionScenario() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 20, 10, 15);
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        AuctionDetailModel auctionDetailModel = iPort.addAuction(auctionCreateModel);
        //then
        assertNotNull(auctionDetailModel.getId());
        verify(auctionOPort, times(1)).save(any());
    }

    // Scenario: some user wants to add an action that does not start or end within allowed hours;
    @Test
    void auctionDoesNotStartWithinAllowedTimeScenario() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 20, 3, 15);
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        //then
        assertThrows(AuctionNotWithinAllowedHoursException.class, () -> iPort.addAuction(auctionCreateModel));
    }

}