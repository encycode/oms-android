package com.encycode.sheetalfoods;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.helper.AllProductsAdapter;

public class ProductActivity extends AppCompatActivity {


    String[] tempSpinnerArray = {"cone","cup","ice cream sandwich","sundae"};
    Spinner spinner;
    RecyclerView recyclerView;
    Button caretCount,addProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        spinner = findViewById(R.id.dropdown_menu);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, tempSpinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        recyclerView = findViewById(R.id.showProductRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setHasFixedSize(true);

        AllProductsAdapter adapter = new AllProductsAdapter(ProductActivity.this);
        adapter.setData("Vanilla","1X10");
        adapter.setData("Chocolate","1X23");
        adapter.setData("Strawberry","1X44");
        adapter.setData("Butter Scotch","1X15");
        adapter.setData("Black Current","1X20");
        adapter.setData("Sitafal","1X25");
        adapter.setData("Rajbhog","1X32");
        adapter.setData("Mava Badaam","1X35");
        adapter.setData("American Dry Fruit","1X23");
        adapter.setData("Pineapple","1X20");
        adapter.setData("Kesar","1X34");
        adapter.setData("Rose","1X20");
        recyclerView.setAdapter(adapter);

        caretCount = findViewById(R.id.caretCountBtn);
        addProduct = findViewById(R.id.addProductBtn);
    }
}