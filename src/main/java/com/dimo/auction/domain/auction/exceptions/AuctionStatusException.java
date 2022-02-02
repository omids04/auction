package com.dimo.auction.domain.auction.exceptions;

public class AuctionStatusException extends RuntimeException{
    public AuctionStatusException() {
        super("based on auction timing biding isn't allowed currently");
    }
}
