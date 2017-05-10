package com.vaibhav.hotstock.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.vaibhav.hotstock.R;
import com.vaibhav.hotstock.login.LoginActivity;
import com.vaibhav.hotstock.models.UserDetail;
import com.vaibhav.hotstock.models.enums.UserType;
import com.vaibhav.hotstock.network.ApiClient;
import com.vaibhav.hotstock.utilities.CommonFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.spinner)
    Spinner role;
    UserType roleValue;

    @Override
    protected void onStop() {
        CommonFunction.getInstance(this).destroy();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        role.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("admin");
        categories.add("broker");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        roleValue = UserType.valueOf(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @OnClick(R.id.submit)
    public void submit() {

        String validate = validate();
        if (validate != null) {
            CommonFunction.getInstance(this).toast(validate);
            return;
        }
        UserDetail userDetail = CreateUser();
        CommonFunction.getInstance(this).showDialog();
        ApiClient.getInstance().getService().register(userDetail).enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                CommonFunction.getInstance(RegistrationActivity.this).dismissDialog();
                if (response.isSuccessful()) {
                    CommonFunction.getInstance(RegistrationActivity.this).toast(getString(R.string.user_registered));
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    CommonFunction.getInstance(RegistrationActivity.this).showError(response);
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                CommonFunction.getInstance(RegistrationActivity.this).dismissDialog();
                CommonFunction.getInstance(RegistrationActivity.this).noInternet(t);

            }
        });

    }

    private UserDetail CreateUser() {

        UserDetail userDetail = new UserDetail();
        userDetail.setName(name.getText().toString());
        userDetail.setRole(roleValue);
        userDetail.setUserId(username.getText().toString());
        return userDetail;
    }

    private String validate() {

        String s = null;
        if (username.getText().toString().length() == 0) {
            s = "please enter username";
        } else if (name.getText().toString().length() == 0) {
            s = "please enter name";
        } else if (roleValue == null) {
            s = "please select role";
        }
        return s;
    }
}
