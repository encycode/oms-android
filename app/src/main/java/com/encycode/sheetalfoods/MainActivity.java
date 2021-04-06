package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.helper.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    Toolbar toolbar;
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

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        CategoryAdapter adapter = new CategoryAdapter(this);
        adapter.setData(1,"Cones");
        adapter.setData(2,"Jumbo Cupes");
        adapter.setData(3,"Premium Big Cups");
        adapter.setData(4,"Big Cup");
        adapter.setData(5,"Sunday");
        adapter.setData(6,"Candy");

        categoryRecyclerView.setAdapter(adapter);
    }
}