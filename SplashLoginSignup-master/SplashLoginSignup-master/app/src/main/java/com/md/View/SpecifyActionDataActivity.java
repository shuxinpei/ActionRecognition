package com.md.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.md.splashloginsignup.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import Util.DataHelpers.SensorRasp;
import Util.SensorUtil;

public class SpecifyActionDataActivity extends AppCompatActivity {
    TextView action_time;
    TextView energy;
    TextView water;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_action_data);
        action_time =(TextView)findViewById(R.id.action_time);
        energy =(TextView)findViewById(R.id.action_energy_consume);
        water =(TextView)findViewById(R.id.action_water_consume);
        time =(TextView)findViewById(R.id.time);
        Intent intent = getIntent();

        long timeaction = intent.getLongExtra("time",1000);

        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
        java.util.Date dt = new Date(timeaction);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00

        Random ra = new Random();

        action_time.setText("运动时长"+sDateTime);
        energy.setText(String.valueOf(SensorRasp.energy));
        water.setText(String.valueOf(SensorRasp.water));
        time.setText(sDateTime);

    }
}
