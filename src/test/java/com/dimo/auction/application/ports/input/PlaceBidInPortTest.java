package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.PlaceBidOutPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// Placing A Bid Scenario: user can place a bid by providing accountId, auctionId and bid price.
// auction exist and is not closed.
// user account exist and have enough credit.
// new price should be higher than current price.
@ExtendWith(MockitoExtension.class)
class PlaceBidInPortTest {

    @Mock
    PlaceBidOutPort placeBidOutPort;

    @InjectMocks
    PlaceBidInPort inPort;

    // main scenario
    @Test
    void placingABidScenario(){

    }

    @Test
    void placingABidForAnAuctionThatDoesNotExists(){

    }

    @Test
    void placingABidAfterAuctionClosed(){

    }

    @Test
    void placingABidForAUserThatDoesNotExist(){

    }

    @Test
    void placingABidWhenAccountDoesNotHaveEnoughCredit(){

    }

    @Test
    void placingABidWithLowerPriceThanCurrent(){

    }
}