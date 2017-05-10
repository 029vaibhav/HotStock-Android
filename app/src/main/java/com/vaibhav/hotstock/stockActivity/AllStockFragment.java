package com.vaibhav.hotstock.stockActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.OnListFragmentInteractionListener;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.enums.StockStatus;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.utilities.CommonFunction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AllStockFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    AllStockAdapter allStockAdapter;
    private Handler mHandler;

    @Override
    public void onStop() {
        super.onStop();
        stopRepeatingTask();
    }

    private void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);

    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllStockFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AllStockFragment newInstance(int columnCount) {
        AllStockFragment fragment = new AllStockFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mHandler = new Handler();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            CommonFunction.getInstance(getContext()).showDialog();
            ApiClient.getInstance().getService().getallStocks(StockStatus.ACTIVE).enqueue(new Callback<List<Stock>>() {
                @Override
                public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                    CommonFunction.getInstance(getContext()).dismissDialog();
                    if (response.isSuccessful()) {
                        List<Stock> body = response.body();
                        CommonFunction.getInstance(getContext()).deleteAll(Stock.class);
                        CommonFunction.getInstance(getContext()).saveObject(getContext(), body);
                        CommonFunction.getInstance(getContext()).updateLastQueried();
                        allStockAdapter = new AllStockAdapter(body, mListener);
                        recyclerView.setAdapter(allStockAdapter);
                        mStatusChecker.run();
                    } else {
                        CommonFunction.getInstance(getActivity()).showError(response);
                    }
                }

                @Override
                public void onFailure(Call<List<Stock>> call, Throwable t) {
                    CommonFunction.getInstance(getContext()).dismissDialog();
                    CommonFunction.getInstance(getContext()).noInternet(t);
                }
            });

        }
        startAlarm();
        return view;
    }

    private void startAlarm() {


    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateStatus(); //this function can change value of mInterval.
            } finally {
                mHandler.postDelayed(mStatusChecker, 30000);
            }
        }
    };

    private void updateStatus() {

        String lastQueriedTime = CommonFunction.getInstance(getContext()).getLastQueriedTime();
        ApiClient.getInstance().getService().getStockUpdate(lastQueriedTime).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    CommonFunction.getInstance(getContext()).updateLastQueried();
                    List<Stock> body = response.body();
                    CommonFunction.getInstance(getContext()).saveObject(getContext(), body);
                    List<Stock> allStock = CommonFunction.getInstance(getContext()).getAllStock();
                    allStockAdapter.updateStock(new ArrayList<>(allStock));
                }
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
