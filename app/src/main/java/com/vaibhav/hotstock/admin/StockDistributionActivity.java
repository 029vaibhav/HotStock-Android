package com.vaibhav.hotstock.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.StockUpdateListener;
import com.vaibhav.hotstock.models.Transaction;
import com.vaibhav.hotstock.models.enums.Status;
import com.vaibhav.hotstock.models.enums.TransactionType;
import com.vaibhav.hotstock.models.enums.UserType;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.utilities.CommonFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockDistributionActivity extends AppCompatActivity {

    @BindView(R.id.total_qty)
    EditText totalQty;
    @BindView(R.id.qty)
    EditText qty;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.stock_id)
    EditText stockId;
    Intent intent;
    List<String> allUsers;
    long totalQtyValue;
    String stockIdValue;

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_distribution);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();


        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().getUserIds(UserType.broker).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                CommonFunction.getInstance(StockDistributionActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    allUsers = response.body();
                    if (allUsers.size() == 0) {
                        CommonFunction.getInstance(StockDistributionActivity.this).toast("No users available returning");
                        finish();
                    } else
                        setupData();
                } else
                    CommonFunction.getInstance(StockDistributionActivity.this).showError(response);

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                CommonFunction.getInstance(StockDistributionActivity.this).dismissDialog();
                CommonFunction.getInstance(StockDistributionActivity.this).noInternet(t);

            }
        });


    }

    @OnClick(R.id.submit)
    public void onSubmit() {

        if (allUsers.size() == 0) {
            CommonFunction.getInstance(this).toast("Reload page, user list not available");
            return;
        }
        String validatedata = validatedata();
        if (validatedata != null) {
            CommonFunction.getInstance(this).toast(validatedata);
            return;
        }

        Transaction transaction = createTransaction();
        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().createTransaction(transaction).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                CommonFunction.getInstance(StockDistributionActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    CommonFunction.getInstance(StockDistributionActivity.this).toast("Stocks Transferred");
                    getUpdatedStocks();
                } else {
                    CommonFunction.getInstance(StockDistributionActivity.this).showError(response);
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                CommonFunction.getInstance(StockDistributionActivity.this).dismissDialog();
                CommonFunction.getInstance(StockDistributionActivity.this).noInternet(t);

            }
        });

    }

    private void getUpdatedStocks() {

        CommonFunction.getInstance(this).getUpdatedStocks(this, new StockUpdateListener() {
            @Override
            public void update(boolean b) {
                finish();
            }
        });
    }


    private Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setStockId(stockIdValue);
        transaction.setSellerId(CommonFunction.getInstance(this).getUserDetail().getUserId());
        transaction.setQty(Long.parseLong(qty.getText().toString()));
        transaction.setStatus(Status.INITIATED);
        transaction.setTransactionType(TransactionType.OWNER_TRANSFER);
        transaction.setBidPrice(intent.getFloatExtra(getString(R.string.stock_price_key), 0));
        transaction.setBuyerId(username.getText().toString());
        return transaction;
    }

    private String validatedata() {

        String error = null;
        if (username.getText().toString().length() == 0) {
            error = "Please enter user name";
        } else if (!allUsers.contains(username.getText().toString())) {
            error = "Please select users from the available list";
        } else if (qty.getText().toString().length() == 0 || qty.getText().toString().equals("0.0")) {
            error = "qty can not be 0 or empty ";
        } else if (Long.parseLong(qty.getText().toString()) > totalQtyValue) {
            error = "transferable qty should be less";
        }
        return error;
    }

    private void setupData() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, allUsers);
        username.setThreshold(1);
        username.setAdapter(adapter);

        stockIdValue = intent.getStringExtra(getString(R.string.stock_id_key));
        stockId.setText(stockIdValue);
        stockId.setEnabled(false);
        totalQtyValue = intent.getLongExtra(getString(R.string.stock_qty_key), 0);
        totalQty.setText("" + totalQtyValue);
        stockId.setEnabled(false);

    }

}
