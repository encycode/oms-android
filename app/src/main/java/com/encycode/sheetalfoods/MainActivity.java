package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.CategoryAdapter;
import com.encycode.sheetalfoods.helper.LoginRequest;
import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.CategoryRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    Toolbar toolbar;
    private APIService mAPIService;
    private static List<Categories> categoriesList;

    //    List<Category> categoryResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        getSupportActionBar().hide();
        toolbar.setTitle("Categories");
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        toolbar.setTitleTextColor(getColor(R.color.white));
        setActionBar(toolbar);
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
                            setDataAdapter(categoryResponse.get(i).getName(),categoryResponse.get(i).getImage(),categoryResponse.get(i).getStatus(),categoryResponse.get(i).getCreatedAt(),categoryResponse.get(i).getUpdatedAt(),categoryResponse.get(i).getDeletedAt());
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

    public void setDataAdapter(String name,String img,int status,String create,String update,String delete) {
        CategoryAdapter adapter = new CategoryAdapter(this);
        categoriesList.add(new Categories(name,img,status==1,create,update, delete));
        adapter.setData(categoriesList);
        categoryRecyclerView.setAdapter(adapter);
    }
}