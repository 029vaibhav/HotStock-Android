package com.vaibhav.hotstock.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.NotificationListener;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Notification;
import com.vaibhav.hotstock.models.StockOwnerShip;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link StockOwnerShip} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notification> mValues;
    NotificationListener mListener;

    public NotificationAdapter(List<Notification> items, NotificationListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mIdView.setText(mValues.get(position).getStockId());
        holder.mContentView.setText(mValues.get(position).getBidPrice().toString());

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
        public Notification mItem;

        public ViewHolder(View view) {
            super(view);
            mView = (Button) view.findViewById(R.id.buy);
            mView.setText(R.string.accept);
            mIdView = (TextView) view.findViewById(R.id.name);
            mContentView = (TextView) view.findViewById(R.id.bid_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
