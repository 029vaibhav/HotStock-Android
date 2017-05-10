package com.vaibhav.hotstock.stockActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.StockOwnerShip;
import com.vaibhav.hotstock.models.Transaction;
import com.vaibhav.hotstock.models.enums.Status;
import com.vaibhav.hotstock.models.enums.TransactionType;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.notification.NotificationActivity;
import com.vaibhav.hotstock.utilities.CommonFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockActivity extends AppCompatActivity implements OnListFragmentInteractionListener {


    @BindView(R.id.pager_report)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs_report)
    TabLayout tabLayout;
    String bidPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ButterKnife.bind(this);
        TabsPagerAdapterStock tabsPagerAdapterStock = new TabsPagerAdapterStock(this.getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapterStock);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent aboutIntent = new Intent(StockActivity.this, NotificationActivity.class);
                startActivity(aboutIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Stock item) {
        Log.d(this.getClass().getName(), item.toString());
        createDialog(item);
    }

    private void createDialog(final Stock item) {

        bidPrice = null;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StockActivity.this);
        alertDialog.setTitle("Buy");
        alertDialog.setMessage("Price enter Bid Price");

        final EditText input = new EditText(StockActivity.this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(8, 8, 8, 8);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String s = input.getText().toString();
                        if (s.equals("" + 0) || s.length() == 0) {
                            CommonFunction.getInstance(StockActivity.this).toast("Enter proper bid price");
                        } else {
                            bidPrice = s;
                            CommonFunction.getInstance(StockActivity.this).showDialog();
                            Transaction transaction = createTransaction(item, Float.parseFloat(bidPrice));
                            ApiClient.getInstance().getService().createTransaction(transaction).enqueue(new Callback<Transaction>() {
                                @Override
                                public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                                    CommonFunction.getInstance(StockActivity.this).dismissDialog();
                                    if (response.isSuccessful()) {
                                        CommonFunction.getInstance(StockActivity.this).toast("Request sent to users");
                                    } else {
                                        CommonFunction.getInstance(StockActivity.this).showError(response);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Transaction> call, Throwable t) {
                                    CommonFunction.getInstance(StockActivity.this).dismissDialog();
                                    CommonFunction.getInstance(StockActivity.this).noInternet(t);
                                }
                            });
                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    private Transaction createTransaction(Stock item, float v) {
        Transaction transaction = new Transaction();
        transaction.setStockId(item.getStockId());
        transaction.setBuyerId(CommonFunction.getInstance(this).getUserDetail().getUserId());
        transaction.setQty(1);
        transaction.setStatus(Status.INITIATED);
        transaction.setTransactionType(TransactionType.BUY);
        transaction.setBidPrice(v);
        return transaction;
    }

    @Override
    public void onListFragmentInteraction(StockOwnerShip item) {
        Log.d(this.getClass().getName(), item.toString());
        CommonFunction.getInstance(this).toast("Feature not available");
    }
}
