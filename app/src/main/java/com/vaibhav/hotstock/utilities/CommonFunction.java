package com.vaibhav.hotstock.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.interfaces.StockUpdateListener;
import com.vaibhav.hotstock.models.ErrorResponse;
import com.vaibhav.hotstock.models.Stock;
import com.vaibhav.hotstock.models.StockOwnerShip;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.network.ApiClient;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 7/5/17.
 */
public class CommonFunction {
    private static CommonFunction ourInstance;

    Context context;
    UserDetail userDetail;
    ProgressDialog progressDialog;
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";


    public static CommonFunction getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new CommonFunction(context);
        }
        return ourInstance;

    }

    private CommonFunction(Context context) {
        this.context = context;
    }

    public void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void destroy() {
        ourInstance = null;
    }

    public UserDetail getUserDetail() {


        Realm realm = Realm.getDefaultInstance();
        UserDetail userId = realm.where(UserDetail.class).equalTo("id", 0).findFirst();
        userDetail = userId;
        return userDetail;
    }


    public void showDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }


    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void noInternet(Throwable throwable) {
        if (throwable instanceof IOException) {
            toast(context.getString(R.string.internet_not_connected));
        }
    }

    public void showError(Response response) {

        try {
            ErrorResponse errorResponse = new ObjectMapper().readValue(response.errorBody().byteStream(), ErrorResponse.class);
            CommonFunction.getInstance(context).toast(errorResponse.getMessage());
        } catch (IOException e) {
            CommonFunction.getInstance(context).toast("Something went wrong");
            e.printStackTrace();
        }

    }

    public void login(String userId, Callback callback) {
        ApiClient.getInstance().getService().login(userId).enqueue(callback);
    }

    public <T> RealmObject saveObject(Context context, RealmObject realmObject) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmObject realmObject1 = realm.copyToRealm(realmObject);
        realm.commitTransaction();
        return realmObject1;
    }

    public <T> RealmObject saveObject(Context context, UserDetail userDetail) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmList<StockOwnerShip> stockOwnerShips = new RealmList<>();
        for (int i = 0; i < userDetail.getStockOwnerShipList().size(); i++) {
            StockOwnerShip stockOwnerShip = realm.copyToRealm(userDetail.getStockOwnerShipList().get(i));
            stockOwnerShips.add(stockOwnerShip);
        }
        userDetail.setStockOwnerShipList(stockOwnerShips);
        RealmObject realmObject1 = realm.copyToRealm(userDetail);
        realm.commitTransaction();
        return realmObject1;
    }

    public void saveObject(Context context, List<Stock> realmObject) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < realmObject.size(); i++) {
            realm.copyToRealmOrUpdate(realmObject.get(i));// Persist unmanaged objects
        }
        realm.commitTransaction();
    }

    public List<Stock> getAllStock() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Stock> all = realm.where(Stock.class).findAll();
        List<Stock> stocks = new ArrayList<>(all);
        return stocks;

    }

    public void deleteAll(Class<? extends RealmObject> t) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(t);
        realm.commitTransaction();
    }

    public void updateLastQueried() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref), 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.date_key), getCurrentTime());
        editor.commit();
    }

    public String getLastQueriedTime() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref), 0);
        String date = sharedPref.getString(context.getString(R.string.date_key), null);
        if (date == null) {
            date = DateTime.now().minusYears(10).toString(DATE_PATTERN);
        }
        return date;
    }

    private String getCurrentTime() {
        return DateTime.now().withZone(DateTimeZone.UTC).minusSeconds(5).toString(DATE_PATTERN);
    }

    public void getUpdatedStocks(final Context context, final StockUpdateListener stockUpdateListener) {

        CommonFunction.getInstance(context).showDialog();
        String userId = CommonFunction.getInstance(context).getUserDetail().getUserId();
        CommonFunction.getInstance(context).login(userId, new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                CommonFunction.getInstance(context).dismissDialog();
                if (response.isSuccessful()) {
                    UserDetail body = response.body();
                    CommonFunction.getInstance(context).deleteAll(UserDetail.class);
                    CommonFunction.getInstance(context).saveObject(context, body);

                } else {
                    CommonFunction.getInstance(context).toast("Please Login again");
                }
                stockUpdateListener.update(true);
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                stockUpdateListener.update(false);
                CommonFunction.getInstance(context).dismissDialog();
                CommonFunction.getInstance(context).noInternet(t);
            }
        });
    }
}
