package com.dimo.auction.domain.auction.exceptions;

public class AuctionClosedException extends RuntimeException{
    public AuctionClosedException() {
        super("based on auction timing biding isn't allowed currently");
    }
}
