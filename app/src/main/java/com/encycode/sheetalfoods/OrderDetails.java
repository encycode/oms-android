package com.encycode.sheetalfoods;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.ProductsAdapter;
import com.encycode.sheetalfoods.helper.request.Order;
import com.encycode.sheetalfoods.helper.request.OrderDetailsDeleteRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.viewmodels.OrderDetailsViewModel;
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
    TextView caretItemglobal;
    List<Products> productsList = new ArrayList<>();
    List<ProductTypes> productTypesList = new ArrayList<>();

    ProductsViewModel productsViewModel;
    ProductTypesViewModel productTypesViewModel;
    OrderDetailsViewModel orderDetailsViewModel;

    RecyclerView recyclerView;
    List<com.encycode.sheetalfoods.entity.OrderDetails> orderDetailsFinal;

    Orders currentOrder;

    int selectedProductTypeId, selectedProductId;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);




        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        productTypesViewModel = ViewModelProviders.of(this).get(ProductTypesViewModel.class);

        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        //Toast.makeText(this, products.size()+"", Toast.LENGTH_SHORT).show();

        button = findViewById(R.id.floatingActionButton);

        currentOrder =(Orders)getIntent().getSerializableExtra("currentOrder");



        orderDetailsViewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel.class);
        orderDetailsViewModel.getSpecificOrderDetails(currentOrder.getId()).observe(this, new Observer<List<com.encycode.sheetalfoods.entity.OrderDetails>>() {
            @Override
            public void onChanged(List<com.encycode.sheetalfoods.entity.OrderDetails> orderDetails) {
                Toast.makeText(OrderDetails.this, ""+currentOrder.getId(), Toast.LENGTH_SHORT).show();
                for(int i=0;i<orderDetails.size();i++) {
                    orderDetailsFinal.add(orderDetails.get(i));
                }
            }
        });

        ProductsAdapter adapter = new ProductsAdapter(OrderDetails.this,currentOrder.getId());
//        Toast.makeText(this, ""+orderDetailsFinal.size(), Toast.LENGTH_SHORT).show();
        //adapter.setOrderDetails(orderDetailsFinal);
//        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button add;
                ImageButton close;
                ArrayList<String> productTypeNames;
                ArrayList<Integer> productTypeIds;
                ArrayList<String> productsNames = new ArrayList<>();
                ArrayList<Integer> productsIds = new ArrayList<>();
                productTypeNames = new ArrayList<>();
                productTypeIds = new ArrayList<>();
                productTypesViewModel.getAllProductTypes().observe(OrderDetails.this, new Observer<List<ProductTypes>>() {
                    @Override
                    public void onChanged(List<ProductTypes> productTypes) {
                        for (int i = 0; i < productTypes.size(); i++) {
                            productTypeNames.add(productTypes.get(i).getName());
                            productTypeIds.add(productTypes.get(i).getId());
                            productTypesList.add(productTypes.get(i));
                        }
                    }
                });
                AutoCompleteTextView productType, product;
                EditText caretOrder;
                TextView caretItem, totalItem;
                addProduct = new Dialog(OrderDetails.this);
                addProduct.setContentView(R.layout.add_product_popup_design);
                totalItem = addProduct.findViewById(R.id.caretTotalItemTV);
                caretOrder = addProduct.findViewById(R.id.caretCount);
                add = addProduct.findViewById(R.id.addBtn);
                close = addProduct.findViewById(R.id.closeBtn);
                addProduct.setCancelable(true);
                caretItem = addProduct.findViewById(R.id.caretItemTV);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(addProduct.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                addProduct.show();
                addProduct.getWindow().setAttributes(lp);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProduct.dismiss();
                    }
                });

                ArrayAdapter<String> productTypesAdapter = new ArrayAdapter<>(OrderDetails.this, R.layout.dropdown_item, productTypeNames);

                productType = addProduct.findViewById(R.id.productTypeDropdown);
                product = addProduct.findViewById(R.id.productsDropdown);

                productType.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        for (int i = 0; i < productTypeNames.size(); i++) {
                            if (productTypeNames.get(i).equals(productType.getText().toString())) {
                                selectedProductTypeId = i+1;
                                break;
                            }
                        }
                        productsIds.clear();
                        productsNames.clear();
                        productsList.clear();
                        productsViewModel.getAllProductsForSpecificProductType(selectedProductTypeId).observe(OrderDetails.this, new Observer<List<Products>>() {
                            @Override
                            public void onChanged(List<Products> products) {
                                for (int i = 0; i < products.size(); i++) {
                                    productsNames.add(products.get(i).getName());
                                    productsIds.add(products.get(i).getId());
                                    productsList.add(products.get(i));
                                }
                            }
                        });
                    }
                });


                ArrayAdapter<String> productAdapter = new ArrayAdapter<>(OrderDetails.this, R.layout.dropdown_item, productsNames);

                productType.setAdapter(productTypesAdapter);
                product.setAdapter(productAdapter);

                product.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        for (int i = 0; i < productsNames.size(); i++) {
                            if (productsNames.get(i).equals(product.getText().toString())) {
                                selectedProductId = i;
                                break;
                            }
                        }
                        caretItem.setText(productsList.get(selectedProductId).getCaretItem() + "");
                    }

                });

                caretOrder.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!caretOrder.getText().toString().equals("")) {
                            int count = Integer.valueOf(caretOrder.getText().toString());
                            int productCount = productsList.get(selectedProductId).getCaretItem() * count;
                            totalItem.setText(productCount + "");
                        } else {
                            totalItem.setText("0");
                        }
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPost(currentOrder.getId(),productsList.get(selectedProductId).getId(),Integer.valueOf(caretOrder.getText().toString()));
                        orderDetailsViewModel.insert(new com.encycode.sheetalfoods.entity.OrderDetails(currentOrder.getId(),productsList.get(selectedProductId).getId(),Integer.valueOf(caretOrder.getText().toString())));
                        addProduct.dismiss();
                    }
                });

            }
        });
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


}