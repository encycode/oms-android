package com.encycode.sheetalfoods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.LoginRequest;
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

    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = new Intent(Login.this, MainActivity.class);
        sh = getSharedPreferences("LoginStatus",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if(sh.getBoolean("isLogin",false)) {
            startActivity(i);
            finish();
        }

        mAPIService = ApiUtils.getAPIService();

        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameVal = username.getText().toString();
                passwordVal = password.getText().toString();
                sendPost(usernameVal, passwordVal);
//                if (usernameVal.equals("order") && passwordVal.equals("order")) {
//                    startActivity(i);
//                    myEdit.putBoolean("isLogin",true);
//                    myEdit.commit();
//                    finish();
//                } else {
//                    Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    public void sendPost(String uname, String pass) {
        mAPIService.LoginPostRequest(uname, pass).enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Login Status", "post submitted to API." + response.body().toString());
                        Toast.makeText(Login.this, response.body().getAccessToken(), Toast.LENGTH_SHORT).show();
                        dialogBoxDisplay(response.body().getAccessToken() + "\n" + response.body().getExpiresAt() + "\n" + response.body().getRole(), "Login Status", Login.this);
                    }
                } else{
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Login Status", "post submitted to API." + message.getMessage());
                        dialogBoxDisplay(message.getMessage(), "Login Status", Login.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
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