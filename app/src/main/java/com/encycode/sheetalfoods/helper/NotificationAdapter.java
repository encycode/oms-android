package com.encycode.sheetalfoods.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.encycode.sheetalfoods.R;
import com.encycode.sheetalfoods.entity.Notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    List<Notifications> notifications = new ArrayList<>();
    Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_rv_desgin, parent, false);
        return new NotificationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notifications current = notifications.get(position);

        holder.title.setText(current.getTitle());
        holder.desc.setText(current.getDescription());
        Glide.with(context)
                .load(current.getImage())
                .into(holder.imageView);
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.desc.getVisibility() == View.GONE) {
                    holder.desc.setVisibility(View.VISIBLE);
                    holder.down.setImageResource(R.drawable.up_arrow);
                } else if (holder.desc.getVisibility() == View.VISIBLE) {
                    holder.desc.setVisibility(View.GONE);
                    holder.down.setImageResource(R.drawable.down);
                }
            }
        });
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.desc.getVisibility() == View.GONE) {
                    holder.desc.setVisibility(View.VISIBLE);
                    holder.down.setImageResource(R.drawable.up_arrow);
                } else if (holder.desc.getVisibility() == View.VISIBLE) {
                    holder.desc.setVisibility(View.GONE);
                    holder.down.setImageResource(R.drawable.down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    public Notifications getNotificationAt(int pos) {
        return notifications.get(pos);
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,desc;
        LinearLayout click;
        ImageButton down;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.notificationImage);
            desc = itemView.findViewById(R.id.notificationDesc);
            title = itemView.findViewById(R.id.notificationTitle);
            down = itemView.findViewById(R.id.downBtn);
            click = itemView.findViewById(R.id.click);
        }
    }

}
