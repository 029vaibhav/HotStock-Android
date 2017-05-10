package com.vaibhav.hotstock.stockActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapterStock extends FragmentStatePagerAdapter {


    public TabsPagerAdapterStock(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "All Stocks";
                break;
            case 1:
                title = "Owned Stocks";
                break;

        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = AllStockFragment.newInstance(1);
                break;
            case 1:
                fragment = OwnerStockFragment.newInstance(1);
                break;
        }
        return fragment;

    }


    @Override
    public int getCount() {
        return 2;
    }
}
