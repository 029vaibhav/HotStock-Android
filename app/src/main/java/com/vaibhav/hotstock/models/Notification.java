package com.vaibhav.hotstock.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaibhav.hotstock.models.enums.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    private String id;
    private String stockId;
    private String initiatorUserId;
    private List<String> notifiedUserIds = new ArrayList<>();
    private BigDecimal bidPrice;
    private String transactionId;
    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(String initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public List<String> getNotifiedUserIds() {
        return notifiedUserIds;
    }

    public void setNotifiedUserIds(List<String> notifiedUserIds) {
        this.notifiedUserIds = notifiedUserIds;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
