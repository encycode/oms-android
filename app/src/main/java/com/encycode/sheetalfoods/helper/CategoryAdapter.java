package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.encycode.sheetalfoods.DealerCreateOrder;
import com.encycode.sheetalfoods.Login;
import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.StaffCreateOrder;
import com.encycode.sheetalfoods.entity.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Categories> categories = new ArrayList<>();
    Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

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
        holder.title.setText(current.getName());

        Glide.with(context)
                .load(current.getImage())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSharedPreferences sh = new GetSharedPreferences("LoginStatus", context);
                if(sh.getPrefString("role").equals("dealer")) {
                    Intent i = new Intent(context, DealerCreateOrder.class);
                    Log.i("category Adapter id", "onClick: { " + current.getId() + "\n" + current.getName() + "\n }" );
                    i.putExtra("cat_id",current.getId());
                    i.putExtra("cat_name",current.getName());
                    context.startActivity(i);
                } else if(sh.getPrefString("role").equals("staff")) {
                    Intent i = new Intent(context,StaffCreateOrder.class);
                    context.startActivity(i);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setData(List<Categories> categoriesList) {
        categories = categoriesList;
        notifyDataSetChanged();
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
