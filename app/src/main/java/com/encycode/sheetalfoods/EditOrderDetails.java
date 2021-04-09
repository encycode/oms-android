package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.request.OrderDetailsEditRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOrderDetails extends AppCompatActivity {

    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_details);
        mAPIService = new ApiUtils(EditOrderDetails.this).getAPIService();
        ToggleStatus(2);
    }
    public void ToggleStatus(int order) {
        mAPIService.OrderDetailsEditRequest(order).enqueue(new Callback<OrderDetailsEditRequest>() {
            @Override
            public void onResponse(Call<OrderDetailsEditRequest> call, Response<OrderDetailsEditRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getOrders().getStatus());
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        Toast.makeText(EditOrderDetails.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsEditRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }
}