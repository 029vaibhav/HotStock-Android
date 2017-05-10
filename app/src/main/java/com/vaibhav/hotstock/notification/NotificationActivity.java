package com.vaibhav.hotstock.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.NotificationListener;
import com.vaibhav.hotstock.models.Notification;
import com.vaibhav.hotstock.models.Transaction;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.models.enums.Status;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.stockActivity.StockActivity;
import com.vaibhav.hotstock.utilities.CommonFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements NotificationListener {

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        notificationListener = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String userId = CommonFunction.getInstance(this).getUserDetail().getUserId();
        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().getNotification(userId, Status.INITIATED).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new NotificationAdapter(response.body(), notificationListener));
                } else {
                    CommonFunction.getInstance(NotificationActivity.this).showError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                CommonFunction.getInstance(NotificationActivity.this).noInternet(t);

            }
        });


    }

    @Override
    public void onListFragmentInteraction(Notification item) {
        CommonFunction.getInstance(this).showDialog();
        String userId = CommonFunction.getInstance(this).getUserDetail().getUserId();
        ApiClient.getInstance().getService().acceptSell(item.getTransactionId(), userId).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful()) {
                    getUpdatedStocks();
                    CommonFunction.getInstance(NotificationActivity.this).toast("Transaction successful");
                    Intent aboutIntent = new Intent(NotificationActivity.this, StockActivity.class);
                    startActivity(aboutIntent);
                    finish();
                } else {
                    CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                    CommonFunction.getInstance(NotificationActivity.this).showError(response);
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                CommonFunction.getInstance(NotificationActivity.this).noInternet(t);

            }
        });
    }

    private void getUpdatedStocks() {

        String userId = CommonFunction.getInstance(this).getUserDetail().getUserId();
        CommonFunction.getInstance(this).login(userId, new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    UserDetail body = response.body();
                    CommonFunction.getInstance(NotificationActivity.this).deleteAll(UserDetail.class);
                    CommonFunction.getInstance(NotificationActivity.this).saveObject(NotificationActivity.this, body);
                    finish();
                } else
                    CommonFunction.getInstance(NotificationActivity.this).toast("Please Login again");
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                CommonFunction.getInstance(NotificationActivity.this).dismissDialog();
                CommonFunction.getInstance(NotificationActivity.this).noInternet(t);
            }
        });
    }
}
