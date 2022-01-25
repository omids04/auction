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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemVO itemVO = (ItemVO) o;

        return itemId.equals(itemVO.itemId);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }
}
