package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.request.OrderDetailsDeleteRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.helper.request.OrderPostRequest;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity {

    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        mAPIService = new ApiUtils(OrderDetails.this).getAPIService();

//        sendPost(2,4,18);

        deleteOrderDetetails(2);
    }
    public void sendPost(int order, int product, int carat_order) {
        mAPIService.OrderDetailsPostRequest(order,product,carat_order).enqueue(new Callback<OrderDetailsRequest>() {
            @Override
            public void onResponse(Call<OrderDetailsRequest> call, Response<OrderDetailsRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getProduct().getCaratOrder());
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        Toast.makeText(OrderDetails.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }

    public void deleteOrderDetetails(int id) {
        mAPIService.OrderDetailsDeleteRequest(id).enqueue(new Callback<OrderDetailsDeleteRequest>() {
            @Override
            public void onResponse(Call<OrderDetailsDeleteRequest> call, Response<OrderDetailsDeleteRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Delete Order Request", "post submitted to API." + response.body().getMessage());
                    }
                    if (response.code() == 201) {
                        Log.i("Delete Order Request", "post submitted to API." + response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Delete Order Request", "post submitted to API." + message.getMessage());
                        Toast.makeText(OrderDetails.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsDeleteRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }
}