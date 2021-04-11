package com.encycode.sheetalfoods.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.encycode.sheetalfoods.R;

public class ProgressLoading {

    private Activity activity;
    private AlertDialog alertDialog;

    public ProgressLoading(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);


        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_loading_dialog,null);
        builder.setView(view);
        builder.setCancelable(false);

        ImageView cone = view.findViewById(R.id.coneLoading);

        RotateAnimation animation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,.5f, Animation.RELATIVE_TO_SELF,.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(5000);
        cone.setAnimation(animation);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    public void endLoading() {
        alertDialog.dismiss();

    }
}
