package com.vaibhav.hotstock.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.Transaction;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Stock} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TradeReportAdapter extends RecyclerView.Adapter<TradeReportAdapter.ViewHolder> {

    private List<Transaction> mValues;

    public TradeReportAdapter(List<Transaction> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trade_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Transaction transaction = mValues.get(position);
        holder.stockId.setText(transaction.getStockId());
        holder.buyer.setText(transaction.getBuyerId());
        holder.seller.setText(transaction.getSellerId());
        holder.qty.setText(String.format("%d", transaction.getQty()));
        holder.price.setText(String.format("%s", transaction.getBidPrice()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stockId, buyer, seller, qty, price;

        public ViewHolder(View view) {
            super(view);
            stockId = (TextView) view.findViewById(R.id.stock_id);
            buyer = (TextView) view.findViewById(R.id.buyer);
            seller = (TextView) view.findViewById(R.id.seller);
            qty = (TextView) view.findViewById(R.id.qty);
            price = (TextView) view.findViewById(R.id.bid_price);
        }

    }
}
