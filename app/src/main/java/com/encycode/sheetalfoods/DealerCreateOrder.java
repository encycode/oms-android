package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.GetSharedPreferences;
import com.encycode.sheetalfoods.helper.ProgressLoading;
import com.encycode.sheetalfoods.helper.request.Order;
import com.encycode.sheetalfoods.helper.request.OrderPostRequest;
import com.encycode.sheetalfoods.viewmodels.OrdersViewModel;
import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerCreateOrder extends AppCompatActivity {

    TextView shop_name, mobile, category;
    Button btnCreateOrder;
    Intent i;
    OrdersViewModel viewModel;
    Orders orders;
    private APIService mAPIService;
    ProgressLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_create_order);

        loading = new ProgressLoading(DealerCreateOrder.this);
        getSupportActionBar().hide();

        shop_name = findViewById(R.id.shopNameTV);
        mobile = findViewById(R.id.mobileNoTV);
        category = findViewById(R.id.categoryTV);
        btnCreateOrder = findViewById(R.id.btnCreateOrder);

        viewModel = new OrdersViewModel(getApplication());
        i = getIntent();
        mAPIService = new ApiUtils(DealerCreateOrder.this).getAPIService();
        GetSharedPreferences getSh = new GetSharedPreferences("LoginStatus", DealerCreateOrder.this);

        shop_name.setText(getSh.getPrefString("shop_name"));
        Log.i("get shop name", "onCreate: " + getSh.getPrefString("shop_name"));
        mobile.setText(getSh.getPrefString("mobile"));
        category.setText(i.getExtras().getString("cat_name"));

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost(getSh.getPrefString("role"),i.getExtras().getInt("cat_id"));
            }
        });
    }

    public void sendPost(String orderBy, int category) {
        loading.startLoading();
        Log.i("log send post", "sendPost: " + orderBy + " ---- " + category);
        mAPIService.OrderPostRequest(orderBy, category).enqueue(new Callback<OrderPostRequest>() {
            @Override
            public void onResponse(Call<OrderPostRequest> call, Response<OrderPostRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getOrders().getOrderNumber());
                        orders = new Orders(response.body().getOrders().getId(),response.body().getOrders().getSenderClientid(),response.body().getOrders().getReceiverClientid(),response.body().getOrders().getShopName(),response.body().getOrders().getAddress(),response.body().getOrders().getMobile(),response.body().getOrders().getOrderby(),response.body().getOrders().getCategoryId(),response.body().getOrders().getStatus(),response.body().getOrders().getOrderNumber(),response.body().getOrders().getUserId(),response.body().getOrders().getCreatedAt(),response.body().getOrders().getUpdatedAt(),response.body().getOrders().getDeletedAt());
                        viewModel.insert(orders);
                        loading.endLoading();
                        intentToOrderDetails();
                    }
                } else {
                    if (response.code() == 401) {
                        loading.endLoading();
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        Toast.makeText(DealerCreateOrder.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderPostRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }

    public void intentToOrderDetails() {
        Intent i = new Intent(DealerCreateOrder.this,OrderDetails.class);
        i.putExtra("currentOrder",orders);
        startActivity(i);
    }

}