package com.encycode.sheetalfoods.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.entity.Products;
import com.encycode.sheetalfoods.helper.request.OrderDetailsDeleteRequest;
import com.encycode.sheetalfoods.viewmodels.OrderDetailsViewModel;
import com.encycode.sheetalfoods.viewmodels.ProductsViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    Context context;
    int orderId;
    private APIService mAPIService;
    List<com.encycode.sheetalfoods.entity.OrderDetails> orderDetails = new ArrayList<>();
    List<Products> productsFinal = new ArrayList<>();
    ProductsViewModel productsViewModel;
    OrderDetailsViewModel orderDetailsViewModel;
    public ProductsAdapter(Context context,int orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_rv_design, parent, false);
        mAPIService = new ApiUtils(context).getAPIService();
        orderDetailsViewModel = new OrderDetailsViewModel((Application) context.getApplicationContext());
        productsViewModel = ViewModelProviders.of((FragmentActivity) context).get(ProductsViewModel.class);
        productsViewModel.getSpecificProduct(orderId).observe((LifecycleOwner) context, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                for(int i=0;i<products.size();i++) {
                    productsFinal.add(products.get(i));
                }
            }
        });
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.productName.setText(productsFinal.get(0).getName());
        holder.caretItems.setText(productsFinal.get(0).getCaretItem());
        holder.caretOrder.setText(orderDetails.get(position).getCaretOrder());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrderDetetails(orderDetails.get(position).getId(),orderDetails.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        TextView productName,caretItems,caretOrder;
        ImageButton delete;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTV);
            caretItems = itemView.findViewById(R.id.productCaretItemsTV);
            caretOrder = itemView.findViewById(R.id.productCaretOrdersTV);
            delete = itemView.findViewById(R.id.removeProductBTN);
        }
    }

    public void deleteOrderDetetails(int id,OrderDetails orderDetails) {
        orderDetailsViewModel.delete(orderDetails);
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
                        Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
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
