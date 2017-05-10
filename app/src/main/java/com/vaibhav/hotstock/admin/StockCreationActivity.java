package com.vaibhav.hotstock.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.StockUpdateListener;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.StockInventory;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.utilities.CommonFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockCreationActivity extends AppCompatActivity {

    @BindView(R.id.stock_id)
    EditText stockId;
    @BindView(R.id.symbol)
    EditText symbol;
    @BindView(R.id.total_qty)
    EditText totalQty;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.base_price)
    EditText basePrice;

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_creation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void submit() {

        String s = validateData();
        if (s != null) {
            CommonFunction.getInstance(this).toast(s);
            return;
        }
        Stock stock = createStock();
        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().createStock(stock).enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                CommonFunction.getInstance(StockCreationActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    CommonFunction.getInstance(StockCreationActivity.this).toast("stock created Successfully");
                    getUpdatedStocks();
                } else {
                    CommonFunction.getInstance(StockCreationActivity.this).showError(response);
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                CommonFunction.getInstance(StockCreationActivity.this).dismissDialog();
                CommonFunction.getInstance(StockCreationActivity.this).noInternet(t);
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


    private Stock createStock() {

        Stock stock = new Stock();
        stock.setStockId(stockId.getText().toString());
        stock.setName(name.getText().toString());
        stock.setSymbol(symbol.getText().toString());
        stock.setBasePrice(Float.parseFloat(basePrice.getText().toString()));
        stock.setCreator(CommonFunction.getInstance(this).getUserDetail().getUserId());
        stock.setStockInventory(new StockInventory());
        stock.getStockInventory().setTotalQty(Long.parseLong(totalQty.getText().toString()));
        return stock;
    }

    private String validateData() {

        String s = null;
        if (stockId.getText().toString().length() == 0) {
            s = "stock id can not be empty";
        } else if (symbol.getText().toString().length() == 0) {
            s = "symbol can not be empty";
        } else if (totalQty.getText().toString().length() == 0) {
            s = "totalQty can not be empty";
        } else if (totalQty.getText().toString().equals("0")) {
            s = "totalQty can not be 0";
        } else if (name.getText().toString().length() == 0) {
            s = "name can not be empty";
        } else if (basePrice.getText().toString().length() == 0) {
            s = "basePrice can not be empty";
        } else if (basePrice.getText().toString().equals("0")) {
            s = "basePrice can not be 0";
        }
        return s;
    }
}
