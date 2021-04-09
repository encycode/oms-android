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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.CategoryAdapter;
import com.encycode.sheetalfoods.helper.GetSharedPreferences;
import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.CategoryRequest;
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
                        GetSharedPreferences  loginShared = new GetSharedPreferences("LoginStatus",MainActivity.this);
                        loginShared.setPrefString("name","");
                        loginShared.setPrefString("username","");
                        loginShared.setPrefString("role","");
                        loginShared.setPrefString("shop_name","");
                        loginShared.setPrefString("address","");
                        loginShared.setPrefString("mobile","");
                        loginShared.setPrefString("token", "");
                        loginShared.setPrefString("token_type","");
                        loginShared.setPrefString("expires_at","");
                        loginShared.setPrefBoolean("isLogin", false);
                        Intent k = new Intent(MainActivity.this,Login.class);
                        startActivity(k);
                        break;
                }
                return true;
            }
        });

        mAPIService = new ApiUtils(MainActivity.this).getAPIService();

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        categoriesList = new ArrayList<>();


        mAPIService.CategoryGetRequest().enqueue(new Callback<CategoryRequest>() {
            @Override
            public void onResponse(Call<CategoryRequest> call, Response<CategoryRequest> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("cat", "onResponse: " + response.body().getMessage());
                        List<Category> categoryResponse = response.body().getCategories();
                        Log.d("Size of List", "onResponse: " + categoryResponse.size());
                        for (int i = 0; i < categoryResponse.size(); i++) {
                            setDataAdapter(categoryResponse.get(i).getName(), categoryResponse.get(i).getImage(), categoryResponse.get(i).getStatus(), categoryResponse.get(i).getCreatedAt(), categoryResponse.get(i).getUpdatedAt(), categoryResponse.get(i).getDeletedAt(), categoryResponse.get(i).getId());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryRequest> call, Throwable t) {
                Log.e("error", t.getMessage().toString());
            }
        });
    }

    public void setDataAdapter(String name, String img, int status, String create, String update, String delete, int id) {
        CategoryAdapter adapter = new CategoryAdapter(this);
        categoriesList.add(new Categories(name, img, status == 1, create, update, delete, id));
        adapter.setData(categoriesList);
        categoryRecyclerView.setAdapter(adapter);
    }
}