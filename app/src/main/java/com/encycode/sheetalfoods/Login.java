package com.encycode.sheetalfoods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    Button login;
    TextInputEditText username, password;
    String usernameVal, passwordVal;
    SharedPreferences sh;

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

        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameVal = username.getText().toString();
                passwordVal = password.getText().toString();
                if (usernameVal.equals("order") && passwordVal.equals("order")) {
                    startActivity(i);
                    myEdit.putBoolean("isLogin",true);
                    myEdit.commit();
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}