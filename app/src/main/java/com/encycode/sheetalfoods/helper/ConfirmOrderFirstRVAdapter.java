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

import java.util.List;

class ConfirmOrderFirstRVAdapte extends RecyclerView.Adapter<ConfirmOrderFirstRVAdapte.ConfirmOrderFirstRVHolder> {

    List<NestedRecyclerViewModel> arrayList;
    Context context;

    public ConfirmOrderFirstRVAdapte(Context context) {
        this.context = context;
    }

    public void setData(List<NestedRecyclerViewModel> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public ConfirmOrderFirstRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_type_design_rv, parent, false);
        return new ConfirmOrderFirstRVHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderFirstRVHolder holder, int position) {
        NestedRecyclerViewModel current = arrayList.get(position);

        ConfirmOrderSecondRVAdapter adapter = new ConfirmOrderSecondRVAdapter(context);

        holder.productType.setText(current.getProductType());
        holder.nestedRV.setHasFixedSize(true);
        holder.nestedRV.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        holder.nestedRV.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ConfirmOrderFirstRVHolder extends RecyclerView.ViewHolder {
        TextView productType;
        RecyclerView nestedRV;
        public ConfirmOrderFirstRVHolder(@NonNull View itemView) {
            super(itemView);
            nestedRV = itemView.findViewById(R.id.selectedProductsRV);
            productType = itemView.findViewById(R.id.productTypeTV);
        }
    }
}
