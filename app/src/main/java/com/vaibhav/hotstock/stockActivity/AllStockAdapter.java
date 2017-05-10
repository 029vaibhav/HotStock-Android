package com.vaibhav.hotstock.stockActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Stock;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Stock} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AllStockAdapter extends RecyclerView.Adapter<AllStockAdapter.ViewHolder> {

    private List<Stock> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AllStockAdapter(List<Stock> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateStock(List<Stock> items) {

        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getStockId());
        holder.bidprice.setText("" + mValues.get(position).getStockInventory().getLastTradedPrice());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final Button mView;
        public final TextView name;
        public final TextView bidprice;
        public Stock mItem;

        public ViewHolder(View view) {
            super(view);
            mView = (Button) view.findViewById(R.id.buy);
            ;
            name = (TextView) view.findViewById(R.id.name);
            bidprice = (TextView) view.findViewById(R.id.bid_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + bidprice.getText() + "'";
        }
    }
}
