package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

public class StaffCreateOrder extends AppCompatActivity {

    Toolbar toolbar;
    AutoCompleteTextView dropDownList;
    private TextInputLayout textInputLayout;

    String[] itemDropDown = {"Select Dealer Name","Darshan Ice Cream","Shubham Ice Cream","Hiren Ice Cream","Darshit Ice Cream"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_create_order);

        dropDownList = findViewById(R.id.dealerNameDropdown);
        textInputLayout = findViewById(R.id.dealerName_dropdown);
        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("Create Order");
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item,itemDropDown);
        dropDownList.setAdapter(adapter);
    }
}