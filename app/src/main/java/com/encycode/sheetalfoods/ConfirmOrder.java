package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.Order;
import com.encycode.sheetalfoods.helper.request.OrderRequest;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    private APIService mAPIService;
    private static List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        recyclerView = findViewById(R.id.productTypeRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAPIService = new ApiUtils(ConfirmOrder.this).getAPIService();
        orderList = new ArrayList<>();


        mAPIService.OrderGetRequest().enqueue(new Callback<OrderRequest>() {
            @Override
            public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                Toast.makeText(ConfirmOrder.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Order> ordersResponse = response.body().getOrders();
                        Log.d("response message", "onResponse: " + response.body().getMessage());

                        for (int i = 0; i < ordersResponse.size(); i++) {
//                            Log.d("record", "onResponse: { "+
                            int order_id = ordersResponse.get(i).getId();
                            int sender_clientid = ordersResponse.get(i).getSenderClientid();
                            int receiver_clientid = ordersResponse.get(i).getReceiverClientid();
                            String shop_name = ordersResponse.get(i).getShopName();
                            String address = ordersResponse.get(i).getAddress();
                            String mobile = ordersResponse.get(i).getMobile();
                            String orderBy = ordersResponse.get(i).getOrderby();
                            int categoryID = ordersResponse.get(i).getCategoryId();
                            String status = ordersResponse.get(i).getStatus();
                            String orderNumber = ordersResponse.get(i).getOrderNumber();
                            int userID = ordersResponse.get(i).getUserId();
                            String createdAt = ordersResponse.get(i).getCreatedAt();
                            String updatedAt = ordersResponse.get(i).getUpdatedAt();
                            String deletedAt = ordersResponse.get(i).getDeletedAt();
//                                    " } ");
//                            setDataAdapter(categoryResponse.get(i).getName(), categoryResponse.get(i).getImage(), categoryResponse.get(i).getStatus(), categoryResponse.get(i).getCreatedAt(), categoryResponse.get(i).getUpdatedAt(), categoryResponse.get(i).getDeletedAt());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderRequest> call, Throwable t) {

            }
        });
    }
}