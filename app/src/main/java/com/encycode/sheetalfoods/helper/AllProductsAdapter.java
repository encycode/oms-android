package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;

import java.util.ArrayList;
import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.AllProductHolder> {

    List<String> productNames = new ArrayList<>();
    List<String> productPerCaret = new ArrayList<>();

    Context context;

    public AllProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AllProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_products_rv_design, parent, false);
        return new AllProductsAdapter.AllProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductHolder holder, int position) {
        holder.name.setText(productNames.get(position));
        holder.pcQuan.setText(productPerCaret.get(position));
    }

    @Override
    public int getItemCount() {
        return productNames.size();
    }

    public void setData(String name,String perCaretQuan) {
        productNames.add(name);
        productPerCaret.add(perCaretQuan);
    }

    public class AllProductHolder extends RecyclerView.ViewHolder {
        TextView name,caret,pcQuan;
        EditText order;
        public AllProductHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productNameTV);
            pcQuan = itemView.findViewById(R.id.perCaretQuanTV);

            order = itemView.findViewById(R.id.orderTV);
        }
    }
}
