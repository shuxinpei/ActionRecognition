package com.md.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.md.splashloginsignup.R;

public class HipActivity extends AppCompatActivity {
    private Button xiadun,
                    tuishangdeng,
                    juanfu,
                    baoxi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hip);
        xiadun = (Button) findViewById(R.id.xiadun);
        tuishangdeng = (Button) findViewById(R.id.tuishangdeng);
        juanfu = (Button) findViewById(R.id.juanfu);
        baoxi = (Button) findViewById(R.id.baoxi);
        xiadun.setOnClickListener(new ActionOnClickListener());
    }
    class ActionOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HipActivity.this,StartSpecifyActionActivity.class);
            if(view == xiadun){
                intent.putExtra("fragment","xiadun");
                startActivity(intent);
            }else if (view == tuishangdeng){

            }else if (view == juanfu){

            }else if (view == baoxi){

            }

        }
    }
}
