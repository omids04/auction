package com.dimo.auction.infrastructure.config;

import com.dimo.auction.application.ports.input.commands.AuctionCommandsInputPort;
import com.dimo.auction.application.ports.input.queries.AuctionQueriesInputPort;
import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.ports.output.CurrentTimeOutputPort;
import com.dimo.auction.application.usecases.commands.AuctionCreateUC;
import com.dimo.auction.application.usecases.commands.BidingUC;
import com.dimo.auction.application.usecases.commands.ChangeAuctionTimingUC;
import com.dimo.auction.application.usecases.queries.AuctionQueries;
import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.infrastructure.adaptor.output.AuctionOutputAdaptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AuctionConfiguration {
    @Bean
    public AuctionOutputPort auctionOutputPort(){
        return new AuctionOutputAdaptor();
    }

    @Bean
    public CurrentTimeOutputPort currentTimeOutputPort(){
        return LocalDateTime::now;
    }

    @Bean
    public AuctionCreateUC auctionCreateUC(){
        return new AuctionCommandsInputPort(auctionOutputPort(), currentTimeOutputPort());
    }

    @Bean
    public ChangeAuctionTimingUC changeAuctionTimingUC(){
        return new AuctionCommandsInputPort(auctionOutputPort(), currentTimeOutputPort());
    }

    @Bean
    public BidingUC bidingUC(){
        return new AuctionCommandsInputPort(auctionOutputPort(), currentTimeOutputPort());
    }

    @Bean
    public AuctionQueries auctionQueries(){
        return new AuctionQueriesInputPort(auctionOutputPort(), currentTimeOutputPort());
    }
}
