package com.encycode.sheetalfoods.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.ConfirmOrder;
import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.request.Order;

import java.util.ArrayList;
import java.util.List;

public class ViewOrdersAdapter extends RecyclerView.Adapter<ViewOrdersAdapter.ViewOrderHolder>  {

    List<Orders> allOrders = new ArrayList<>();
    Context context;

    public ViewOrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_orders_rv_designm, parent, false);
        return new ViewOrderHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    public void onBindViewHolder(@NonNull ViewOrderHolder holder, int position) {
        Orders currentOrder = allOrders.get(position);

        holder.shopName.setText(currentOrder.getShopName());
        holder.orderId.setText(currentOrder.getOrderNumber());
        holder.status.setText(currentOrder.getStatus());
        String btnStatus;
        if(currentOrder.getStatus().equals("Cancelled")) {
            holder.orderOpBtn.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
            holder.orderOpBtn.setText("Reorder");
            btnStatus = "reorder";
        } else {
            holder.orderOpBtn.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
            holder.orderOpBtn.setText("Cancel");
            btnStatus = "cancel";
        }

        holder.orderOpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setMessage("Do you really want to "+btnStatus+"?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(btnStatus.equals("reorder")) {
                                    //reorder
                                } else if (btnStatus.equals("cancel")){
                                    //cancel
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ConfirmOrder.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allOrders.size();
    }

    public void setAllOrders(List<Orders> allOrders) {
        this.allOrders = allOrders;
    }

    public class ViewOrderHolder extends RecyclerView.ViewHolder {
        TextView shopName,orderId,status;
        Button orderOpBtn;
        public ViewOrderHolder(@NonNull View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.shopNameRVTV);
            orderId = itemView.findViewById(R.id.orderIdTV);
            status = itemView.findViewById(R.id.orderStatusTV);
            orderOpBtn = itemView.findViewById(R.id.orderOpBtn);
        }
    }
}
