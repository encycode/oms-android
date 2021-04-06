package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.StaffCreateOrder;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Integer> images = new ArrayList<>();
    List<String>  titles = new ArrayList<>();

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
            holder.title.setText(titles.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, StaffCreateOrder.class);
                    context.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void setData(int image,String title) {
        titles.add(title);
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
