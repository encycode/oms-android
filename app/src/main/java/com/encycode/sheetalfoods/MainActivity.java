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

        List<Categories> categoriesList = new ArrayList<>();

        mAPIService.CategoryGetRequest().enqueue(new Callback<CategoryRequest>() {
            @Override
            public void onResponse(Call<CategoryRequest> call, Response<CategoryRequest> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("cat", "onResponse: "+ response.body().getMessage());
                        List<Category> categoryResponse = response.body().getCategories();
                        for (int i=0; i<categoryResponse.size(); i++) {
                            String name = categoryResponse.get(i).getName();
                            String image = categoryResponse.get(i).getImage();
                            boolean status = categoryResponse.get(i).getStatus() == 1;
                            String createdAt = categoryResponse.get(i).getCreatedAt();
                            String updatedAt = categoryResponse.get(i).getUpdatedAt();
                            String deletedAt = categoryResponse.get(i).getDeletedAt();
                            if(deletedAt == null) {
                                deletedAt = "null";
                            }
                            Log.d("Api Response", "onResponse: { \n" + name + "\n" + image + "\n" + status + "\n"+ createdAt + "\n" + updatedAt  + "\n" + deletedAt + "\n" + " }" );
                            Categories data = new Categories(name,image,status,createdAt,updatedAt,deletedAt);
                            categoriesList.add(data);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryRequest> call, Throwable t) {
                Log.e("error", t.getMessage().toString());
            }
        });
        CategoryAdapter adapter = new CategoryAdapter(this);

        categoriesList.add(new Categories("Milk Products","qwerty",true,"10 March","12 March","null"));
        categoriesList.add(new Categories("IceCream","qwerty",true,"10 March","12 March","null"));
        categoriesList.add(new Categories("Frozen Products","qwerty",true,"10 March","12 March","null"));
        adapter.setData(categoriesList);
        Log.d("Category Adapter", "onCreate: " + adapter.toString());
        categoryRecyclerView.setAdapter(adapter);

    }
}