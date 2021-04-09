package com.encycode.sheetalfoods.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.helper.request.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    List<Notification> notifications = new ArrayList<>();
    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_rv_desgin, parent, false);
        return new NotificationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification current  = notifications.get(position);

        holder.title.setText(current.getTitle());
        holder.desc.setText(current.getDesc());
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.desc.getVisibility() == View.GONE)
                    holder.desc.setVisibility(View.VISIBLE);
                if(holder.desc.getVisibility() == View.VISIBLE)
                    holder.desc.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,desc;
        ImageButton down;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.notificationImage);
            desc = itemView.findViewById(R.id.notificationDesc);
            title = itemView.findViewById(R.id.notificationTitle);
            down = itemView.findViewById(R.id.downBtn);
        }
    }
}
