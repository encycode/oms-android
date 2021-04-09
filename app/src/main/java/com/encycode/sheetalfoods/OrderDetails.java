package com.encycode.sheetalfoods;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.request.OrderDetailsDeleteRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.viewmodels.ProductTypesViewModel;
import com.encycode.sheetalfoods.viewmodels.ProductsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity {

    Dialog addProduct;
    FloatingActionButton button;
    List<Products> products = new ArrayList<>();
    List<ProductTypes> productTypes = new ArrayList<>();

    ProductsViewModel productsViewModel;
    ProductTypesViewModel productTypesViewModel;

   ArrayList<String> productsNames, productTypeNames;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        mAPIService = new ApiUtils(OrderDetails.this).getAPIService();

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        productsViewModel.getAllProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                setProducts(products);
            }
        });

        productTypesViewModel = ViewModelProviders.of(this).get(ProductTypesViewModel.class);
        productTypesViewModel.getAllProductTypes().observe(this, new Observer<List<ProductTypes>>() {
            @Override
            public void onChanged(List<ProductTypes> productTypes) {
                setProductTypes(productTypes);
            }
        });

        for (int i = 0; i < productTypes.size(); i++) {
            productTypeNames.add(productTypes.get(i).getName());
        }
        for (int i = 0; i < products.size(); i++) {
            productsNames.add(products.get(i).getName());
        }


        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView productType, product;
                EditText caretOrder;
                TextView caretItem, totalItem;
                addProduct = new Dialog(OrderDetails.this);
                addProduct.setContentView(R.layout.add_product_popup_design);
                addProduct.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(addProduct.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                addProduct.show();
                addProduct.getWindow().setAttributes(lp);

                ArrayAdapter<String> productAdapter = new ArrayAdapter<>(OrderDetails.this, R.layout.dropdown_item,productsNames);
                ArrayAdapter<String> productTypesAdapter = new ArrayAdapter<>(OrderDetails.this, R.layout.dropdown_item,productTypeNames);

                productType = addProduct.findViewById(R.id.productTypeDropdown);
                product = addProduct.findViewById(R.id.productsDropdown);

                productType.setAdapter(productTypesAdapter);
                product.setAdapter(productAdapter);


            }
        });

//        sendPost(2,4,18);

        deleteOrderDetetails(2);
    }

    public void sendPost(int order, int product, int carat_order) {
        mAPIService.OrderDetailsPostRequest(order, product, carat_order).enqueue(new Callback<OrderDetailsRequest>() {
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

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public void setProductTypes(List<ProductTypes> productTypes) {
        this.productTypes = productTypes;
    }
}