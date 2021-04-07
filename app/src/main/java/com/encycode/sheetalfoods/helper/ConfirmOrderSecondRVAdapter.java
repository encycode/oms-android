package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.entity.Products;

import java.util.List;

public class ConfirmOrderSecondRVAdapter extends RecyclerView.Adapter<ConfirmOrderSecondRVAdapter.ConfirmOrderSecondRVHolder> {

    List<Products> arrayList;
    Context context;

    public ConfirmOrderSecondRVAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Products> list) {
        arrayList = list;
    }
    @NonNull
    @Override
    public ConfirmOrderSecondRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_products_rv_design, parent, false);
        return new ConfirmOrderSecondRVHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderSecondRVHolder holder, int position) {
        Products current = arrayList.get(position);

        holder.productName.setText(current.getName());
        holder.productName.setText(current.getCaretItem());
        holder.productName.setText(String.valueOf(current.getCaretItem()*3));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ConfirmOrderSecondRVHolder extends RecyclerView.ViewHolder{
        TextView productName,caretCount,total;
        public ConfirmOrderSecondRVHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameRVTV);
            caretCount = itemView.findViewById(R.id.productCaretCount);
            total = itemView.findViewById(R.id.productToatalCount);
        }
    }
}
