package com.encycode.sheetalfoods;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.helper.GetSharedPreferences;
import com.encycode.sheetalfoods.helper.ProgressLoading;

public class ProfileActivity extends AppCompatActivity {

    TextView userFullName,userRole,shpName,address,mobile;
    Button changePass,logout;
    GetSharedPreferences loginShared;
    Dialog dialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar();
        intialise();
        setData();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Do you really want to Log Out ?")
                        .setIcon(R.drawable.logout)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                logout();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePassDialog();
            }
        });

    }

    private void toolbar() {
        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);
    }

    private void openChangePassDialog() {

        dialog = new Dialog(ProfileActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.change_password);
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        EditText oldPass,newPass,confPass;
        TextView oldPassError,newPassError,confPassError;
        Button change;
        ImageButton close;

        oldPass = dialog.findViewById(R.id.oldPassword);
        newPass = dialog.findViewById(R.id.newPassword);
        confPass = dialog.findViewById(R.id.confPassword);

        oldPassError = dialog.findViewById(R.id.oldPasswordError);
        newPassError = dialog.findViewById(R.id.newPasswordError);
        confPassError = dialog.findViewById(R.id.confPasswordError);

        change = dialog.findViewById(R.id.changeBtn);

        close = dialog.findViewById(R.id.closeBtn);

        oldPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(oldPass.getText().toString().length() < 8) {
                        oldPassError.setVisibility(View.VISIBLE);
                    } else {
                        if(oldPassError.getVisibility() == View.VISIBLE) {
                            oldPassError.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        newPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(newPass.getText().toString().length() < 8) {
                        newPassError.setVisibility(View.VISIBLE);
                    } else {
                        if(newPassError.getVisibility() == View.VISIBLE) {
                            newPassError.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        confPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(confPass.getText().toString().equals(newPass.getText().toString())) {
                        confPassError.setVisibility(View.VISIBLE);
                    } else {
                        if(confPassError.getVisibility() == View.VISIBLE) {
                            confPassError.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPassError.getVisibility() != View.VISIBLE && newPassError.getVisibility() != View.VISIBLE && confPassError.getVisibility() != View.VISIBLE) {
                    changePassword(newPass.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void changePassword(String toString) {
    }

    private void intialise() {

        userFullName= findViewById(R.id.userFullName);
        userRole = findViewById(R.id.userRole);
        shpName = findViewById(R.id.userShopName);
        mobile = findViewById(R.id.userMobile);
        address = findViewById(R.id.userAddress);
        changePass = findViewById(R.id.changePassword);
        logout = findViewById(R.id.logoutBtn);
        loginShared = new GetSharedPreferences("LoginStatus", ProfileActivity.this);
    }

    public void setData() {

        userFullName.setText(loginShared.getPrefString("name"));
        userRole.setText(loginShared.getPrefString("role"));
        shpName.setText(loginShared.getPrefString("shop_name"));
        mobile.setText(loginShared.getPrefString("mobile"));
        address.setText(loginShared.getPrefString("address"));
    }

    public void logout() {

        loginShared.setPrefString("name", "");
        loginShared.setPrefString("username", "");
        loginShared.setPrefString("role", "");
        loginShared.setPrefString("shop_name", "");
        loginShared.setPrefString("address", "");
        loginShared.setPrefString("mobile", "");
        loginShared.setPrefString("token", "");
        loginShared.setPrefString("token_type", "");
        loginShared.setPrefString("expires_at", "");
        loginShared.setPrefBoolean("isLogin", false);
        Intent k = new Intent(ProfileActivity.this, Login.class);
        startActivity(k);
    }
}