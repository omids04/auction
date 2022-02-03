package com.dimo.auction.infrastructure.adaptor.input.servlet;

import com.dimo.auction.application.usecases.commands.AuctionCreateUC;
import com.dimo.auction.application.usecases.commands.BidingUC;
import com.dimo.auction.application.usecases.commands.ChangeAuctionTimingUC;
import com.dimo.auction.application.usecases.queries.AuctionQueries;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.infrastructure.adaptor.input.servlet.model.req.AuctionCreateModel;
import com.dimo.auction.infrastructure.adaptor.input.servlet.model.req.TimingUpdateModel;
import com.dimo.auction.infrastructure.adaptor.input.servlet.model.res.AuctionDetailsModel;
import com.dimo.auction.infrastructure.adaptor.input.servlet.model.res.BidCreateModel;
import com.dimo.auction.infrastructure.adaptor.input.servlet.model.res.BidDetailsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuctionInputAdaptor {

    private final AuctionCreateUC auctionCreateUC;
    private final ChangeAuctionTimingUC changeAuctionTimingUC;
    private final BidingUC bidingUC;
    private final AuctionQueries auctionQueries;

    @PostMapping("auction")
    @ResponseStatus(HttpStatus.CREATED)
    public AuctionDetailsModel create(AuctionCreateModel model){
        var itemId = Id.of(model.getItemId());
        var accountId = Id.of(model.getAccountId());
        var basePrice = new Price(model.getPrice());
        var timing = new AuctionTiming(model.getStartTime(), model.getDuration());
        var auction = auctionCreateUC.create(itemId, accountId, basePrice, timing);
        return this.toDetailsModel(auction);
    }

    @PutMapping("acution/{id}/timing")
    @ResponseStatus(HttpStatus.OK)
    public AuctionDetailsModel updateTiming(@PathVariable("id") UUID id, TimingUpdateModel model){
        var timing = new AuctionTiming(model.getStartTime(), Duration.ofMinutes(model.getDuration()));
        var auction = changeAuctionTimingUC.updateTiming(Id.of(id), timing);
        return this.toDetailsModel(auction);
    }

    @PostMapping("auction/{id}/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public BidDetailsModel placeBid(@PathVariable("id") UUID id,BidCreateModel model){
        var bid = bidingUC.placeBid(Id.of(id), Id.of(model.getAccountId()), new Price(model.getPrice()));
        int rank = 1;
        return BidDetailsModel
                .builder()
                .accountId(bid.getAccountId().getId())
                .id(bid.getId().getId())
                .price(bid.getPrice().getAmount())
                .rank(rank)
                .build();
    }

    @GetMapping("auction")
    @ResponseStatus(HttpStatus.OK)
    public List<AuctionDetailsModel> auctionList(){
        var auctions = this.auctionQueries.allAuctions();
        return auctions
                .stream()
                .map(this::toDetailsModel)
                .collect(Collectors.toList());
    }

    private AuctionDetailsModel toDetailsModel(Auction auction){
        return AuctionDetailsModel
                .builder()
                .id(auction.getId().getId())
                .accountId(auction.getAccountId().getId())
                .itemId(auction.getItemId().getId())
                .bidCount(auction.getBids().highestIdAssigned().getId())
                .duration(auction.getTiming().getDuration().toMinutes())
                .startTime(auction.getTiming().getStartTime())
                .basePrice(auction.getBids().basePrice().getAmount())
                .currentHighest(auction.getBids().highestBid().isEmpty()?
                        auction.getBids().basePrice().getAmount() : auction.getBids().highestBid().get().getPrice().getAmount())
                .build();
    }
}
