package com.encycode.sheetalfoods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.CategoryAdapter;
import com.encycode.sheetalfoods.helper.GetSharedPreferences;
import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.CategoryRequest;
import com.encycode.sheetalfoods.helper.request.Product;
import com.encycode.sheetalfoods.helper.request.ProductType;
import com.encycode.sheetalfoods.viewmodels.CategoriesViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private APIService mAPIService;
    private static List<Categories> categoriesList;
    CategoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav_menu);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_start, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view_order_menu:
                        Intent i = new Intent(MainActivity.this, ViewOrders.class);
                        startActivity(i);
                        break;

                    case R.id.notification_menu:
                        Intent j = new Intent(MainActivity.this, Notifications.class);
                        startActivity(j);
                        break;

                    case R.id.logout:
                        GetSharedPreferences loginShared = new GetSharedPreferences("LoginStatus", MainActivity.this);
                        loginShared.setPrefString("name", "");
                        loginShared.setPrefString("username", "");
                        loginShared.setPrefString("role", "");
                        loginShared.setPrefString("shop_name", "");
                        loginShared.setPrefString("address", "");
                        loginShared.setPrefString("mobile", "");
                        loginShared.setPrefString("token", "");
                        loginShared.setPrefString("token_type", "");
                        loginShared.setPrefString("expires_at", "");
                        loginShared.setPrefBoolean("isLogin", false);
                        Intent k = new Intent(MainActivity.this, Login.class);
                        startActivity(k);
                        break;
                }
                return true;
            }
        });

        mAPIService = new ApiUtils(MainActivity.this).getAPIService();

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        CategoryAdapter adapter = new CategoryAdapter(this);
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.getAllCategories().observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {
                adapter.setData(categories);
            }
        });
        categoryRecyclerView.setAdapter(adapter);

        mAPIService.CategoryGetRequest().enqueue(new Callback<CategoryRequest>() {
            @Override
            public void onResponse(Call<CategoryRequest> call, Response<CategoryRequest> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("cat", "onResponse: " + response.body().getMessage());
                        List<Category> categoryResponse = response.body().getCategories();
                        List<Product> productListResponse = response.body().getProducts();
                        List<ProductType> productTypeListResponse = response.body().getProductTypes();
                        Log.d("Size Category List ", "onResponse: " + categoryResponse.size());
                        Log.d("Size Product List ", "onResponse: " + productListResponse.size());
                        Log.d("Size Product Type List", "onResponse: " + productTypeListResponse.size());
                        for (int i = 0; i < categoryResponse.size(); i++) {
                            setDataAdapter(categoryResponse.get(i).getName(), categoryResponse.get(i).getImage(), categoryResponse.get(i).getStatus().intValue(), categoryResponse.get(i).getCreatedAt(), categoryResponse.get(i).getUpdatedAt(), categoryResponse.get(i).getDeletedAt(), categoryResponse.get(i).getId().intValue());
                        }
                        for (int i = 0; i < productListResponse.size(); i++) {
                            int id = productListResponse.get(i).getId().intValue();
                            int product_type_id = productListResponse.get(i).getProductTypeId().intValue();
                            String name = productListResponse.get(i).getName();
                            String image = productListResponse.get(i).getImage();
                            int status = productListResponse.get(i).getStatus().intValue();
                            int carat_item = productListResponse.get(i).getCaratItem().intValue();
                            int carat_price = productListResponse.get(i).getCaratPrice().intValue();
                            String created_at = productListResponse.get(i).getCreatedAt();
                            String updated_at = productListResponse.get(i).getUpdatedAt();
                            String deleted_at = productListResponse.get(i).getDeletedAt();
                            Log.i("Product Log", "onResponse: { \n" + id + "\n" + product_type_id + "\n" + name + "\n" +
                                    image + "\n" + status + "\n" + carat_item + "\n" + carat_price + "\n" + created_at + "\n" + updated_at + "\n"
                                    + deleted_at + "\n }"
                            );
                        }
                        for (int i = 0; i < productTypeListResponse.size(); i++) {
                            int id = productTypeListResponse.get(i).getId().intValue();
                            int category_id = productTypeListResponse.get(i).getCategoryId().intValue();
                            String name = productTypeListResponse.get(i).getName();
                            int status = productTypeListResponse.get(i).getStatus().intValue();
                            String created_at = productTypeListResponse.get(i).getCreatedAt();
                            String updated_at = productTypeListResponse.get(i).getUpdatedAt();
                            String deleted_at = productTypeListResponse.get(i).getDeletedAt();

                            Log.i("Product Type Log", "onResponse: { \n" + id + "\n" + category_id + "\n" + name + "\n" + status + "\n" + created_at + "\n" + updated_at + "\n"
                                    + deleted_at + "\n }"
                            );
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void setDataAdapter(String name, String img, int status, String create, String update, String delete, int id) {
        viewModel.insert(new Categories(name, img, status == 1, create, update, delete, id));
    }
}