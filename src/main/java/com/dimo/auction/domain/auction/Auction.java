package com.dimo.auction.domain.auction;

public class Auction {

    private AuctionId id;
    private ItemVO item;
    private AuctionPricingVO pricing;
    private AuctionTimingVO timing;

    public Auction(ItemVO item, AuctionPricingVO pricing, AuctionTimingVO timing) {
        this.item = item;
        this.pricing = pricing;
        this.timing = timing;
        this.id = new AuctionId();
    }

    public AuctionId id() {
        return id;
    }

    public ItemVO item() {
        return item;
    }

    public AuctionPricingVO pricingDetails() {
        return pricing;
    }

    public AuctionTimingVO timingDetails() {
        return timing;
    }
}
