package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.StaffCreateOrder;
import com.encycode.sheetalfoods.entity.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Categories> categories = new ArrayList<>();

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview_design, parent, false);

        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Categories current = categories.get(position);
        Log.d("Category Adapter", "onBindViewHolder: " + current.getName());
        holder.title.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setData(List<Categories> categoriesList) {
        categories = categoriesList;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
            title = itemView.findViewById(R.id.categoryName);
        }
    }
}
