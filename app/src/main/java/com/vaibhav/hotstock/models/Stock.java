package com.vaibhav.hotstock.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaibhav.hotstock.models.enums.StockStatus;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock extends RealmObject {

    @JsonProperty(value = "id")
    @PrimaryKey
    private String stockId;
    private String name;
    private String symbol;
    private float basePrice;
    private StockInventory stockInventory;
    //    @JsonSerialize(using = CustomDateTimeSerializer.class)
//    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private String publishDate;
    private String stockStatus;
    private String creator;

    public Stock() {
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id='" + stockId + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", basePrice=" + basePrice +
                ", stockInventory=" + stockInventory +
                ", publishDate=" + publishDate +
                ", stockStatus=" + stockStatus +
                '}';
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public StockInventory getStockInventory() {
        return stockInventory;
    }

    public void setStockInventory(StockInventory stockInventory) {
        this.stockInventory = stockInventory;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public StockStatus getStockStatus() {
        if (stockStatus != null)
            return StockStatus.valueOf(stockStatus);
        return null;
    }

    public void setStockStatus(StockStatus stockStatus) {
        if (stockStatus != null)
            this.stockStatus = stockStatus.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        return getStockId().equals(stock.getStockId());

    }

    @Override
    public int hashCode() {
        return getStockId().hashCode();
    }
}
