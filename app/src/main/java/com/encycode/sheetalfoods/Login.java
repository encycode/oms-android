package com.encycode.sheetalfoods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.GetSharedPreferences;
import com.encycode.sheetalfoods.helper.LoginRequest;
import com.encycode.sheetalfoods.helper.ProgressLoading;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button login;
    TextInputEditText username, password;
    String usernameVal, passwordVal;
    SharedPreferences sh;
    SharedPreferences.Editor myEdit;
    Intent i;
    TextView usernameError,passowrdError;
    private APIService mAPIService;
    ProgressLoading loading;
    boolean send = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        i = new Intent(Login.this, MainActivity.class);
        sh = getSharedPreferences("LoginStatus", MODE_PRIVATE);
        myEdit = sh.edit();

        getSupportActionBar().hide();

        loading = new ProgressLoading(Login.this);

        if (sh.getBoolean("isLogin", false)) {
            startActivity(i);
            finish();
        }

        mAPIService = new ApiUtils(Login.this).getAPIService();

        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        usernameError = findViewById(R.id.usernameError);
        passowrdError = findViewById(R.id.passwordError);

//        username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.toString().equals("")) {
//                    usernameError.setVisibility(View.VISIBLE);
//                    send= false;
//                } else {
//                    usernameError.setVisibility(View.GONE);
//                    send = true;
//                }
//            }
//        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (username.getText().toString().equals("")) {
                        usernameError.setVisibility(View.VISIBLE);
                        send = false;
                    } else {
                        usernameError.setVisibility(View.GONE);
                        send = true;
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (password.getText().toString().length() < 8) {
                        passowrdError.setVisibility(View.VISIBLE);
                        send = false;
                    } else {
                        passowrdError.setVisibility(View.GONE);
                        send = true;
                    }
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameVal = username.getText().toString();
                passwordVal = password.getText().toString();
                if (usernameVal.equals("")) {
                    usernameError.setVisibility(View.VISIBLE);
                    send = false;
                } if (passwordVal.equals("")) {
                    passowrdError.setVisibility(View.VISIBLE);
                    send = false;
                } else {
                    if (send) {
                        usernameError.setVisibility(View.GONE);
                        passowrdError.setVisibility(View.GONE);
                        sendPost(usernameVal, passwordVal);
                    }
                }
            }
        });
    }

    public void sendPost(String uname, String pass) {
        loading.startLoading();
        mAPIService.LoginPostRequest(uname, pass).enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Login Status", "post submitted to API." + response.body().toString());
                        GetSharedPreferences loginShared = new GetSharedPreferences("LoginStatus", Login.this);
                        loginShared.setPrefString("name", response.body().getName());
                        loginShared.setPrefString("username", response.body().getUsername());
                        loginShared.setPrefString("password",pass);
                        loginShared.setPrefString("role", response.body().getRole());
                        loginShared.setPrefString("shop_name", response.body().getShopName());
                        loginShared.setPrefString("address", response.body().getAddress());
                        loginShared.setPrefString("mobile", response.body().getMobile());
                        loginShared.setPrefString("token", response.body().getAccessToken());
                        loginShared.setPrefString("token_type", response.body().getTokenType());
                        loginShared.setPrefString("expires_at", response.body().getExpiresAt());
                        loginShared.setPrefBoolean("isLogin", true);

                        Log.d("Shared Pref", "onResponse: " + loginShared.getPrefString("name"));
                        startActivity(i);
                        loading.endLoading();
                        finish();
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Login Status", "post submitted to API." + message.getMessage());
//                        dialogBoxDisplay(message.getMessage(), "Login Status", Login.this);
                        loading.endLoading();
                        Toast.makeText(Login.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                loading.endLoading();
                Log.e("error", t.getMessage().toString());
            }

        });
    }

    public void dialogBoxDisplay(String msg, String title, Context c) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(true)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}