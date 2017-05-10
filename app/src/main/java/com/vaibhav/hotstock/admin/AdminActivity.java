package com.vaibhav.hotstock.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.StockOwnerShip;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.report.TradeReportActivity;
import com.vaibhav.hotstock.utilities.CommonFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminActivity extends AppCompatActivity implements OnListFragmentInteractionListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    AdminOwnerStockAdapter adminOwnerStockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserDetail byId = CommonFunction.getInstance(this).getUserDetail();
        int size = byId.getStockOwnerShipList().size();
        adminOwnerStockAdapter = new AdminOwnerStockAdapter(byId.getStockOwnerShipList(), this);
        recyclerView.setAdapter(adminOwnerStockAdapter);


    }

    @Override
    protected void onPostResume() {

        UserDetail byId = CommonFunction.getInstance(this).getUserDetail();
        adminOwnerStockAdapter.updateAdapter(byId.getStockOwnerShipList());
        super.onPostResume();
    }

    @OnClick(R.id.fab)
    public void onfabClick() {
        Intent intent = new Intent(this, StockCreationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Stock item) {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.report:
                Intent aboutIntent = new Intent(this, TradeReportActivity.class);
                startActivity(aboutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @Override
    public void onListFragmentInteraction(StockOwnerShip item) {

        Intent intent = new Intent(AdminActivity.this, StockDistributionActivity.class);
        intent.putExtra(getString(R.string.stock_id_key), item.getStockId());
        intent.putExtra(getString(R.string.stock_qty_key), item.getQty());
        intent.putExtra(getString(R.string.stock_price_key), item.getPrice());
        startActivity(intent);
    }
}
