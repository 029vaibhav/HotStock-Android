package com.vaibhav.hotstock.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaibhav.hotstock.models.enums.UserType;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail extends RealmObject {

    @PrimaryKey
    private int id;
    private String userId;
    private String name;
    private String role;
    private RealmList<StockOwnerShip> stockOwnerShipList = new RealmList<>();

    public UserDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getRole() {
        return UserType.valueOf(role);
    }

    public void setRole(UserType role) {
        this.role = role.toString();
    }

    public RealmList<StockOwnerShip> getStockOwnerShipList() {
        return stockOwnerShipList;
    }

    public void setStockOwnerShipList(RealmList<StockOwnerShip> stockOwnerShipList) {
        this.stockOwnerShipList = stockOwnerShipList;
    }
}
