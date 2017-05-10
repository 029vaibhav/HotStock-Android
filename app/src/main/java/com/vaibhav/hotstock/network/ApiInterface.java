package com.vaibhav.hotstock.network;

import com.vaibhav.hotstock.models.Notification;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.Transaction;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.models.enums.Status;
import com.vaibhav.hotstock.models.enums.StockStatus;
import com.vaibhav.hotstock.models.enums.UserType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vaibhav on 20/12/16.
 */
public interface ApiInterface {

    //user
    @POST("user/register")
    Call<UserDetail> register(@Body UserDetail userDetail);

    @GET("user/login/{userId}")
    Call<UserDetail> login(@Path("userId") String userId);

    @GET("user/user-ids/{role}")
    Call<List<String>> getUserIds(@Path("role") UserType userType);

    //stock
    @POST("stock/create")
    Call<Stock> createStock(@Body Stock stock);

    @GET("stock/getall/{status}")
    Call<List<Stock>> getallStocks(@Path("status") StockStatus stockStatus);


    @GET("stock/time")
    Call<List<Stock>> getStockUpdate(@Query("time") String time);

    //transaction
    @POST("transaction/create")
    Call<Transaction> createTransaction(@Body Transaction transaction);

    @POST("transaction/accept/sell/{transactionId}/{sellerId}")
    Call<Transaction> acceptSell(@Path("transactionId") String transactionId, @Path("sellerId") String sellerId);

    @POST("transaction/accept/buy/{transactionId}/{buyerId}")
    Call<Transaction> acceptBuy(@Path("transactionId") String transactionId, @Path("buyerId") String buyerId);

    @GET("transaction/report")
    Call<List<Transaction>> getReport(@Query("start") String start, @Query("end") String end);

    //Notification

    @GET("notification/view/{userId}/{status}")
    Call<List<Notification>> getNotification(@Path("userId") String userId, @Path("status") Status status);


}