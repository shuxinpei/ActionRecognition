package com.md.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.md.View.fragments.shenaFragment;
import com.md.View.fragments.xiadunFragment;
import com.md.splashloginsignup.R;

public class StartSpecifyActionActivity extends AppCompatActivity implements shenaFragment.OnFragmentInteractionListener,xiadunFragment.OnFragmentInteractionListener {
    String fragment;
    Intent intent;
    FragmentManager fragmentmagager;
    Button start;
    Button Watchdata;
    boolean onclick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_specify_action);
        start = (Button)findViewById(R.id.start);
        Watchdata = (Button)findViewById(R.id.Watchdata);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!onclick){
                    onclick = true;
                    start.setText("暂停运动");
                }else{
                    onclick = false;
                    start.setText("开始运动");
                }
            }
        });

        Watchdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!onclick){
                startActivity(new Intent(StartSpecifyActionActivity.this,SpecifyActionDataActivity.class));
                //发送数据
                }else {
                    Toast.makeText(StartSpecifyActionActivity.this, "请先结束运动", Toast.LENGTH_SHORT).show();
                }
            }
        });

        intent = getIntent();
        fragment = intent.getStringExtra("fragment");
        fragmentmagager=this.getFragmentManager();
        FragmentTransaction transaction=fragmentmagager.beginTransaction();
        if(fragment.equals("shena")){
            System.out.println("替换俯卧撑");
            transaction.replace(R.id.blank,new shenaFragment());
            System.out.println("替换成功");
        }else{
            transaction.replace(R.id.blank,new xiadunFragment());
        }
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
