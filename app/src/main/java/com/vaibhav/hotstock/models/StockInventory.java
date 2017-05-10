package com.vaibhav.hotstock.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)

public class StockInventory extends RealmObject{

    private long totalQty;
    private float lastTradedPrice;
//    @JsonSerialize(using = CustomDateTimeSerializer.class)
//    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private String lastUpdatedTime;

    @Override
    public String toString() {
        return "StockInventory{" +
                "totalQty=" + totalQty +
                ", lastTradedPrice=" + lastTradedPrice +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }

    public long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(long totalQty) {
        this.totalQty = totalQty;
    }

    public float getLastTradedPrice() {
        return lastTradedPrice;
    }

    public void setLastTradedPrice(float lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
