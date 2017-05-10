package com.vaibhav.hotstock.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.admin.AdminActivity;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.models.enums.UserType;
import com.vaibhav.hotstock.registration.RegistrationActivity;
import com.vaibhav.hotstock.stockActivity.StockActivity;
import com.vaibhav.hotstock.utilities.CommonFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText username;

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void register() {

        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();

    }


    @OnClick(R.id.login)
    public void loginClick() {

        if (username.getText().length() == 0) {
            CommonFunction.getInstance(this).toast("username can not be empty");
            return;
        }
        CommonFunction.getInstance(this).showDialog();
        CommonFunction.getInstance(this).login(username.getText().toString(), new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                CommonFunction.getInstance(LoginActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    UserDetail body = response.body();
                    CommonFunction.getInstance(LoginActivity.this).deleteAll(UserDetail.class);
                    CommonFunction.getInstance(LoginActivity.this).saveObject(LoginActivity.this, body);
                    startActivity(intentFactory(body));
                    finish();
                } else {
                    CommonFunction.getInstance(LoginActivity.this).showError(response);
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                CommonFunction.getInstance(LoginActivity.this).dismissDialog();
                CommonFunction.getInstance(LoginActivity.this).noInternet(t);
            }
        });

    }

    private Intent intentFactory(UserDetail body) {

        if (body.getRole() == UserType.admin) {
            return new Intent(LoginActivity.this, AdminActivity.class);
        } else return new Intent(LoginActivity.this, StockActivity.class);

    }

}
