package com.md.View.ViewComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

import com.md.splashloginsignup.R;

public class ActionLinearLayout extends LinearLayout {
    private LinearLayout linearLayout;
    //动作gif
    private GifImageView actionGif;
    private TextView action_name;
    private TextView action_timeall;
    private TextView action_energy;
    private TextView action_time;


    public ActionLinearLayout(Context context) {
        super(context);
    }
    public ActionLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        LayoutInflater.from(context).inflate(R.layout.action_data, this, true);
        linearLayout = (LinearLayout) findViewById(R.id.single_action);
        actionGif = (GifImageView)findViewById(R.id.actionGif);
        action_name = (TextView)findViewById(R.id.action_name);
        action_timeall = (TextView)findViewById(R.id.action_timeall);
        action_energy = (TextView)findViewById(R.id.action_energy);
        action_time = (TextView)findViewById(R.id.action_time);

    }


}
