package com.vaibhav.hotstock.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)

public class StockOwnerShip extends RealmObject {

    String stockId;
    long qty;
    float price;
    String name;

    public StockOwnerShip() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockOwnerShip that = (StockOwnerShip) o;

        return getStockId().equals(that.getStockId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return getStockId().hashCode();
    }
}
