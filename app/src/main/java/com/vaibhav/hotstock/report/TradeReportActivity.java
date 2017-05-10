package com.vaibhav.hotstock.report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.models.Transaction;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.utilities.CommonFunction;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vaibhav.hotstock.utilities.CommonFunction.DATE_PATTERN;

public class TradeReportActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_report);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DateTime dateTime1 = new DateTime().withTimeAtStartOfDay();
        DateTime dateTime2 = new DateTime().withTimeAtStartOfDay().plusDays(1);
        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().getReport(dateTime1.toString(DATE_PATTERN), dateTime2.toString(DATE_PATTERN)).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                CommonFunction.getInstance(TradeReportActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new TradeReportAdapter(response.body()));
                } else CommonFunction.getInstance(TradeReportActivity.this).showError(response);
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                CommonFunction.getInstance(TradeReportActivity.this).dismissDialog();
                CommonFunction.getInstance(TradeReportActivity.this).noInternet(t);

            }
        });

    }
}
