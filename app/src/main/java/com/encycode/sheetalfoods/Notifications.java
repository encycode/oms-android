package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.encycode.sheetalfoods.helper.NotificationAdapter;
import com.encycode.sheetalfoods.helper.request.Notification;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    RecyclerView  recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.notificationRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<Notification> notifications = new ArrayList<>();

        notifications.add(new Notification("","Offer 1","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 2","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 3","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 4","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 5","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));

        NotificationAdapter adapter = new NotificationAdapter(this);
        adapter.setNotifications(notifications);

        recyclerView.setAdapter(adapter);
    }
}