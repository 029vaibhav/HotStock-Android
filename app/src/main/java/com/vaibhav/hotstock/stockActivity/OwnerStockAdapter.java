package com.vaibhav.hotstock.stockActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.StockOwnerShip;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link StockOwnerShip} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OwnerStockAdapter extends RecyclerView.Adapter<OwnerStockAdapter.ViewHolder> {

    private final List<StockOwnerShip> mValues;
    private final OnListFragmentInteractionListener mListener;

    public OwnerStockAdapter(List<StockOwnerShip> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_own_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.stockId.setText(holder.mItem.getStockId());
        holder.bidPrice.setText(String.format("%s", holder.mItem.getPrice()));
        holder.quantity.setText(String.format("%d", holder.mItem.getQty()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView quantity;
        public final TextView stockId;
        public final TextView bidPrice;
        public StockOwnerShip mItem;

        public ViewHolder(View view) {
            super(view);
            quantity = (TextView) view.findViewById(R.id.buy);
            stockId = (TextView) view.findViewById(R.id.name);
            bidPrice = (TextView) view.findViewById(R.id.bid_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + bidPrice.getText() + "'";
        }
    }
}
