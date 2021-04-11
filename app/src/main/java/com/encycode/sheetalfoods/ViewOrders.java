package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewAnimator;

import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.CategoryAdapter;
import com.encycode.sheetalfoods.helper.ViewOrdersAdapter;
import com.encycode.sheetalfoods.helper.request.Order;
import com.encycode.sheetalfoods.helper.request.OrderRequest;
import com.encycode.sheetalfoods.viewmodels.OrdersViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.lifecycle.ViewModelProvider.*;

public class ViewOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    private APIService mAPIService;
    private static List<Orders> orderList;
    Toolbar toolbar;
    OrdersViewModel viewModel;
    FloatingActionButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("All Orders");
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);

        btn = findViewById(R.id.floatingActionButton2);
        ViewOrdersAdapter adapter = new ViewOrdersAdapter(this);
        //adapter.setAllOrders();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewOrders.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        recyclerView = findViewById(R.id.viewOrderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewOrders.this));
        recyclerView.setHasFixedSize(true);
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        viewModel.getAllOrders().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                adapter.setAllOrders(orders);
            }
        });
        recyclerView.setAdapter(adapter);

        mAPIService = new ApiUtils(ViewOrders.this).getAPIService();
        orderList = new ArrayList<>();

        mAPIService.OrderGetRequest().enqueue(new Callback<OrderRequest>() {
            @Override
            public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                //Toast.makeText(ViewOrders.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
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
                            Log.i("test Api", "onResponse: " + orderNumber + " -------------- "+shop_name);
                           viewModel.insert(new Orders(order_id,sender_clientid,receiver_clientid,shop_name,address,mobile,orderBy,categoryID,status,orderNumber,userID,createdAt,updatedAt,deletedAt));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderRequest> call, Throwable t) {

            }
        });
    }

    public void setDataAdapter(int order_id,int sender_clientid,int receiver_clientid,String shop_name, String address,String mobile,String orderBy,int categoryID,String status,String orderNumber,int userID,String createdAt, String updatedAt,String deletedAt) {
    }
}