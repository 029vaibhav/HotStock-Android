package com.vaibhav.hotstock.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vaibhav.hotstock.models.enums.Status;
import com.vaibhav.hotstock.models.enums.TransactionType;
import com.vaibhav.hotstock.utilities.CustomDateTimeDeserializer;
import com.vaibhav.hotstock.utilities.CustomDateTimeSerializer;

import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Transaction {

    private String id;
    private String buyerId;
    private String sellerId;
    private String stockId;
    private float bidPrice;
    private TransactionType transactionType;
    private Status status;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime requestedDate;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime acceptedDate;
    private long qty;

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", bidPrice=" + bidPrice +
                ", transactionType=" + transactionType +
                ", status=" + status +
                ", requestedDate=" + requestedDate +
                ", acceptedDate=" + acceptedDate +
                ", qty=" + qty +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(DateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public DateTime getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(DateTime acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
