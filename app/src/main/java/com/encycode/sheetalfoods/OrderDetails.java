package com.encycode.sheetalfoods;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
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
import com.encycode.sheetalfoods.helper.ProgressLoading;
import com.encycode.sheetalfoods.helper.request.OrderDetailsEditRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.viewmodels.OrderDetailsViewModel;
import com.encycode.sheetalfoods.viewmodels.OrdersViewModel;
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
    TextView orderNumberTv, shopNameTv, orderStatusTv;
    List<Products> productsList = new ArrayList<>();
    Button confirm;
    Toolbar toolbar;
    List<ProductTypes> productTypesList = new ArrayList<>();

    LinearLayout headerLayout;

    boolean isDone;
    ProductsViewModel productsViewModel;
    ProductTypesViewModel productTypesViewModel;
    OrderDetailsViewModel orderDetailsViewModel;

    RecyclerView recyclerView;
    List<com.encycode.sheetalfoods.entity.OrderDetails> orderDetailsFinal = new ArrayList<>();

    Orders currentOrder;
    OrdersViewModel ordersViewModel;
    ProgressLoading loading;
    int selectedProductTypeId, selectedProductId;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        loading = new ProgressLoading(OrderDetails.this);
        mAPIService = new ApiUtils(this).getAPIService();


        headerLayout = findViewById(R.id.tableHeader);
        ordersViewModel = new OrdersViewModel(getApplication());

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        productTypesViewModel = ViewModelProviders.of(this).get(ProductTypesViewModel.class);

        recyclerView = findViewById(R.id.productsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        orderNumberTv = findViewById(R.id.od_number);
        orderStatusTv = findViewById(R.id.od_status);
        shopNameTv = findViewById(R.id.od_shopName);
        confirm = findViewById(R.id.confirm_button);


        //Toast.makeText(this, products.size()+"", Toast.LENGTH_SHORT).show();

        button = findViewById(R.id.floatingActionButton);


        currentOrder = (Orders) getIntent().getSerializableExtra("currentOrder");
        ProductsAdapter adapter = new ProductsAdapter(OrderDetails.this, currentOrder.getId());
        orderDetailsViewModel = ViewModelProviders.of(this).get(OrderDetailsViewModel.class);
        orderDetailsViewModel.getSpecificOrderDetails(currentOrder.getId()).observe(this, new Observer<List<com.encycode.sheetalfoods.entity.OrderDetails>>() {
            @Override
            public void onChanged(List<com.encycode.sheetalfoods.entity.OrderDetails> orderDetails) {
                //Toast.makeText(OrderDetails.this, "" + orderDetails.size(), Toast.LENGTH_SHORT).show();
                adapter.setOrderDetails(orderDetails);
                adapter.notifyDataSetChanged();
                if (confirm.getVisibility() == View.GONE) {
                    confirm.setVisibility(View.VISIBLE);
                }
                if (headerLayout.getVisibility() == View.GONE) {
                    headerLayout.setVisibility(View.VISIBLE);
                }
                if (adapter.getItemCount() == 0) {
                    confirm.setVisibility(View.GONE);
                    headerLayout.setVisibility(View.GONE);
                }
            }
        });

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("Order Number: " + currentOrder.getOrderNumber());
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);

        orderNumberTv.setText(currentOrder.getOrderNumber());
        orderStatusTv.setText(currentOrder.getStatus());
        shopNameTv.setText(currentOrder.getShopName());


//        Toast.makeText(this, ""+orderDetailsFinal.size(), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);

        if (currentOrder.getStatus().equals("Confirmed")) {
            confirm.setVisibility(View.GONE);
        } else
            confirm.setVisibility(View.VISIBLE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OrderDetails.this)
                        .setTitle("Confirm Order?")
                        .setMessage("Do you really want to confirm this order?")
                        .setIcon(R.drawable.order)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ToggleStatus(currentOrder.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });


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
                AutoCompleteTextView productType, product;
                EditText caretOrder;
                TextView caretItem, totalItem, productTypeError, productError, caretError;


                addProduct = new Dialog(OrderDetails.this);
                productTypeError = addProduct.findViewById(R.id.productTypeError);
                productError = addProduct.findViewById(R.id.productError);
                caretError = addProduct.findViewById(R.id.caretError);
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
                        productType.clearFocus();
                        for (int i = 0; i < productTypeNames.size(); i++) {
                            if (productTypeNames.get(i).equals(productType.getText().toString())) {
                                selectedProductTypeId = i + 1;
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

                productType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            productType.clearFocus();
                            if (productType.getText().equals("")) {
                                productTypeError.setVisibility(View.VISIBLE);
                                isDone = false;
                            } else {
                                if (productTypeError.getVisibility() == View.VISIBLE) {
                                    productTypeError.setVisibility(View.GONE);
                                }
                                isDone = true;
                            }
                        }
                    }
                });

                product.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            product.clearFocus();
                            if (product.getText().equals("")) {
                                productError.setVisibility(View.VISIBLE);
                                isDone = false;
                            } else {
                                if (productError.getVisibility() == View.VISIBLE) {
                                    productError.setVisibility(View.GONE);
                                }
                                isDone = true;
                            }
                        }
                    }
                });

                caretOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            caretOrder.clearFocus();
                            if (caretOrder.getText().equals("")) {
                                caretError.setVisibility(View.VISIBLE);
                                isDone = false;
                            }
                            if (Integer.parseInt(caretOrder.getText().toString()) == 0) {
                                caretError.setText("Order cannot be of 0 caret.");
                                caretError.setVisibility(View.VISIBLE);
                                isDone = false;
                            }
                            if (caretOrder.getText().toString().contains(".")) {
                                caretError.setText("Caret number must be in decimal");
                                caretError.setVisibility(View.VISIBLE);
                                isDone = true;
                            } else {
                                if (caretError.getVisibility() == View.VISIBLE) {
                                    caretError.setVisibility(View.GONE);

                                }
                                isDone = true;
                            }
                        }
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
                        product.clearFocus();
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
                        if (!caretOrder.getText().toString().equals("")) {
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
                        if (caretOrder.getText().equals("")) {
                            caretError.setVisibility(View.VISIBLE);
                            isDone = false;
                        }
                        if (Integer.parseInt(caretOrder.getText().toString()) == 0) {
                            caretError.setText("Order cannot be of 0 caret.");
                            caretError.setVisibility(View.VISIBLE);
                            isDone = false;
                        }
                        if (caretOrder.getText().toString().contains(".")) {
                            caretError.setText("Caret number must be in decimal");
                            caretError.setVisibility(View.VISIBLE);
                            isDone = true;
                        }
                        if (product.getText().equals("")) {
                            productError.setVisibility(View.VISIBLE);
                            isDone = false;
                        }
                        if (productType.getText().equals("")) {
                            productTypeError.setVisibility(View.VISIBLE);
                            isDone = false;
                        } else {
                            if (isDone)
                                sendPost(currentOrder.getId(), productsList.get(selectedProductId).getId(), Integer.parseInt(caretOrder.getText().toString()));
                            addProduct.dismiss();
                        }
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
                        orderDetailsViewModel.insert(new com.encycode.sheetalfoods.entity.OrderDetails(response.body().getProduct().getId().intValue(), currentOrder.getId(), carat_order, productsList.get(selectedProductId).getCaretPrice(), productsList.get(selectedProductId).getId()));
//                        loading.endLoading();
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

    public void ToggleStatus(int order) {
        loading.startLoading();
        mAPIService.OrderDetailsEditRequest(order).enqueue(new Callback<OrderDetailsEditRequest>() {
            @Override
            public void onResponse(Call<OrderDetailsEditRequest> call, Response<OrderDetailsEditRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        Log.d("Order Response", "onResponse: " + response.body().getOrders().getStatus());
                        currentOrder.setStatus("Confirmed");
                        ordersViewModel.update(currentOrder);
                        if (currentOrder.getStatus().equals("Confirmed")) {
                            confirm.setVisibility(View.GONE);
                        } else {
                            confirm.setVisibility(View.VISIBLE);
                        }
                        loading.endLoading();
                        Intent i = new Intent(OrderDetails.this, ViewOrders.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
                        loading.endLoading();
                        Toast.makeText(OrderDetails.this, message.getMessage(), Toast.LENGTH_SHORT).show();
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