package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.shared.ValueObject;

import java.util.UUID;

public class ItemVO extends ValueObject {

    private UUID itemId;

    public ItemVO(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }
}
