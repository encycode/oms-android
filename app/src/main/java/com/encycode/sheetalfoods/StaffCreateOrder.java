package com.encycode.sheetalfoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.entity.Users;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.ProgressLoading;
import com.encycode.sheetalfoods.helper.request.StaffOrderRequest;
import com.encycode.sheetalfoods.viewmodels.OrdersViewModel;
import com.encycode.sheetalfoods.viewmodels.UsersViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffCreateOrder extends AppCompatActivity {

    Toolbar toolbar;
    AutoCompleteTextView dealerName;
    private TextInputLayout textInputLayout;
    Button btnCreateStaffOrder;
    private APIService mAPIService;
    EditText shopName, mobile, address;
    int dealerId = -1;
    UsersViewModel usersViewModel;
    OrdersViewModel ordersViewModel;
    TextView shopNameError, mobileError, addressError;
    ArrayList<String> dealerNames = new ArrayList<>();
    ArrayList<Integer> dealerIds = new ArrayList<>();
    ProgressLoading loading;
    Orders orders;
    boolean isDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_create_order);
        loading = new ProgressLoading(StaffCreateOrder.this);
        dealerName = findViewById(R.id.dealerNameDropdown);
        textInputLayout = findViewById(R.id.dealerName_dropdown);
        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("Create Order");
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);

        ordersViewModel = new OrdersViewModel(getApplication());

        shopName = findViewById(R.id.shopNameET);
        mobile = findViewById(R.id.shopMobileET);
        address = findViewById(R.id.shopAddressET);

        shopNameError = findViewById(R.id.shopNameError);
        mobileError = findViewById(R.id.mobileError);
        addressError = findViewById(R.id.addressError);
        isDone = true;
        shopNameError.setVisibility(View.GONE);
        mobileError.setVisibility(View.GONE);
        addressError.setVisibility(View.GONE);

        Intent i = getIntent();

        usersViewModel = ViewModelProviders.of(StaffCreateOrder.this).get(UsersViewModel.class);
        usersViewModel.getAllUsersByRole("dealer").observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                for (int i = 0; i < users.size(); i++) {
                    dealerNames.add(users.get(i).getName());
                    dealerIds.add(users.get(i).getId());
                }
            }
        });


        int catId = i.getIntExtra("cat_id", -1);

        mAPIService = new ApiUtils(StaffCreateOrder.this).getAPIService();

        btnCreateStaffOrder = findViewById(R.id.btnCreateOrderStaff);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, dealerNames);
        dealerName.setAdapter(adapter);

        dealerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                dealerName.clearFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dealerName.clearFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                dealerName.clearFocus();
                for (int i = 0; i < dealerNames.size(); i++) {
                    if (s.toString().equals(dealerNames.get(i))) {
                        dealerId = i;
                        //Toast.makeText(StaffCreateOrder.this, ""+dealerIds.get(dealerId), Toast.LENGTH_SHORT).show();
                    }
                }
                //Toast.makeText(StaffCreateOrder.this, "" + dealerId, Toast.LENGTH_SHORT).show();
                if (dealerId != -1) {
                    usersViewModel.getUserById(dealerIds.get(dealerId)).observe(StaffCreateOrder.this, new Observer<List<Users>>() {
                        @Override
                        public void onChanged(List<Users> users) {
                            //Toast.makeText(StaffCreateOrder.this, users.get(0).getShopName(), Toast.LENGTH_SHORT).show();
                            shopName.setText(users.get(0).getShopName());
                            mobile.setText(users.get(0).getMobile());
                            address.setText(users.get(0).getAddress());
                        }
                    });
                }
            }
        });

        dealerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    dealerName.clearFocus();
                }
            }
        });

        shopName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (shopName.getText().toString().equals("")) {
                        shopNameError.setVisibility(View.VISIBLE);
                        isDone = false;
                    } else {
                        shopNameError.setVisibility(View.GONE);
                        isDone = true;
                    }
                }
            }
        });

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (mobile.getText().toString().length() < 10) {
                        mobileError.setVisibility(View.VISIBLE);
                        isDone = false;
                    } else {
                        mobileError.setVisibility(View.GONE);
                        isDone = true;
                    }
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (address.getText().toString().equals("")) {
                        addressError.setVisibility(View.VISIBLE);
                        isDone = false;
                    } else {
                        addressError.setVisibility(View.GONE);
                        isDone = true;
                    }
                }
            }
        });

        btnCreateStaffOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().equals("")) {
                    addressError.setVisibility(View.VISIBLE);
                    isDone = false;
                }
                if (mobile.getText().toString().length() < 10) {
                    mobileError.setVisibility(View.VISIBLE);
                    isDone = false;
                }
                if (shopName.getText().toString().equals("")) {
                    shopNameError.setVisibility(View.VISIBLE);
                    isDone = false;
                } else {
                    if (isDone) {
                        sendPost(dealerId, shopName.getText().toString(), address.getText().toString(), mobile.getText().toString(), "staff", catId);
                        //Toast.makeText(StaffCreateOrder.this, "hii", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void sendPost(int receiver_client, String shop_name, String address, String mobile, String orderby, int category) {
        //loading.startLoading();
        mAPIService.OrderStaffPostRequest(receiver_client, shop_name, mobile, address, orderby, category).enqueue(new Callback<StaffOrderRequest>() {
            @Override
            public void onResponse(Call<StaffOrderRequest> call, Response<StaffOrderRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        //Toast.makeText(StaffCreateOrder.this, "Inserted", Toast.LENGTH_SHORT).show();
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getOrders().getOrderby());
                        Log.d("staff Test", "onResponseOrder: " + response.body().getOrders().getId());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getShopName());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getAddress());
                        Log.d("staff Test", "onResponse: " + response.body().getOrders().getMobile());
                        orders = new Orders(response.body().getOrders().getId().intValue(), response.body().getOrders().getSenderClientid().intValue(), response.body().getOrders().getReceiverClientid().intValue(), response.body().getOrders().getShopName(), response.body().getOrders().getAddress(), response.body().getOrders().getMobile(), response.body().getOrders().getOrderby(), response.body().getOrders().getCategoryId().intValue(), response.body().getOrders().getStatus(), response.body().getOrders().getOrderNumber(), response.body().getOrders().getUserId().intValue(), response.body().getOrders().getCreatedAt(), response.body().getOrders().getUpdatedAt(), response.body().getOrders().getDeletedAt());
                        ordersViewModel.insert(orders);
                        Intent i = new Intent(StaffCreateOrder.this, ViewOrders.class);
                        i.putExtra("currentOrder", orders);
                        //loading.endLoading();
                        startActivity(i);
                        finish();
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        //loading.endLoading();
                        Toast.makeText(StaffCreateOrder.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StaffOrderRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(StaffCreateOrder.this, "hello", Toast.LENGTH_SHORT).show();
                //loading.endLoading();
            }

        });
    }
}