package com.vaibhav.hotstock.interfaces;

import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.StockOwnerShip;

/**
 * Created by vaibhav on 8/5/17.
 */

public interface OnListFragmentInteractionListener {

    void onListFragmentInteraction(Stock item);

    void onListFragmentInteraction(StockOwnerShip item);

}
