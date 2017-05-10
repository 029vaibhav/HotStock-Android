package com.vaibhav.hotstock.admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class AdminOwnerStockAdapter extends RecyclerView.Adapter<AdminOwnerStockAdapter.ViewHolder> {

    private List<StockOwnerShip> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AdminOwnerStockAdapter(List<StockOwnerShip> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateAdapter(List<StockOwnerShip> items) {
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
        holder.mIdView.setText(holder.mItem.getStockId());
        holder.mContentView.setText("" + holder.mItem.getPrice());

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
        public final TextView mIdView;
        public final TextView mContentView;
        public StockOwnerShip mItem;

        public ViewHolder(View view) {
            super(view);
            mView = (Button) view.findViewById(R.id.buy);
            mView.setText(R.string.transfer);
            mIdView = (TextView) view.findViewById(R.id.name);
            mContentView = (TextView) view.findViewById(R.id.bid_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
