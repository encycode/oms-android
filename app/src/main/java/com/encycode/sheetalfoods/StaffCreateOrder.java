package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.helper.request.StaffOrder;
import com.encycode.sheetalfoods.helper.request.StaffOrderRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffCreateOrder extends AppCompatActivity {

    Toolbar toolbar;
    AutoCompleteTextView dropDownList;
    private TextInputLayout textInputLayout;
    Button btnCreateStaffOrder;
    private APIService mAPIService;

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

        mAPIService = new ApiUtils(StaffCreateOrder.this).getAPIService();

        btnCreateStaffOrder = findViewById(R.id.btnCreateOrderStaff);

        btnCreateStaffOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost(3,"HP","Rajkot","1478523695","staff",3);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item,itemDropDown);
        dropDownList.setAdapter(adapter);
    }

    public void sendPost(int receiver_client,String shop_name,String address,String mobile,String orderby,int category) {
        mAPIService.OrderStaffPostRequest(receiver_client,shop_name,mobile,address,orderby,category).enqueue(new Callback<StaffOrderRequest> () {
            @Override
            public void onResponse(Call<StaffOrderRequest> call, Response<StaffOrderRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getOrders().getOrderby());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getId());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getShopName());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getAddress());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getMobile());
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        Toast.makeText(StaffCreateOrder.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StaffOrderRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }
}