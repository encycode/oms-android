package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;

import org.w3c.dom.Text;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ProductTypeHolder> {

    Context context;

    public ProductTypeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_type_design_rv, parent, false);

        return new ProductTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeHolder holder, int position) {
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        holder.productType.setText("Anything");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductTypeHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView productType;
        public ProductTypeHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.productTypeRV);
            productType = itemView.findViewById(R.id.productTypeTV);

        }
    }
}
