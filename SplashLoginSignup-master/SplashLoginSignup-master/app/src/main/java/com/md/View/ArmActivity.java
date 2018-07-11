package com.md.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.md.splashloginsignup.R;

public class ArmActivity extends AppCompatActivity {
    private Button shena,
                    juanfu,
                    baoshoujuanfu,
                    baoxi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm);
        shena = (Button) findViewById(R.id.shena);
        juanfu = (Button) findViewById(R.id.juanfu);
        baoshoujuanfu = (Button) findViewById(R.id.baoshoujuanfu);
        baoxi = (Button) findViewById(R.id.baoxi);
    }

    class ActionOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view == shena){
            //跳转，同时传递数据过去
            }else if (view == baoshoujuanfu){

            }else if (view == juanfu){

            }else if (view == baoxi){

            }
        }
    }
}

