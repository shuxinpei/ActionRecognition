package com.md.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.md.View.fragments.ActionCountFragment;
import com.md.View.fragments.BodyDataFragment;
import com.md.View.fragments.HistoryActionFragment;
import com.md.View.fragments.shenaFragment;
import com.md.View.fragments.xiadunFragment;
import com.md.splashloginsignup.R;

public class LeftViewActivity extends AppCompatActivity implements ActionCountFragment.OnFragmentInteractionListener,
        BodyDataFragment.OnFragmentInteractionListener,
        HistoryActionFragment.OnFragmentInteractionListener{

    private Button bodydatast;
    private Button history;
    private Button count;
    private Button logout;

    FragmentManager fragmentmagager;
    String fragment;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_view);
        bodydatast = (Button)findViewById(R.id.bodydataset);
        history = (Button)findViewById(R.id.history);
        count = (Button)findViewById(R.id.count);
        logout = (Button)findViewById(R.id.logout);

        bodydatast.setOnClickListener(new leftOnClickListener());
        history.setOnClickListener(new leftOnClickListener());
        count.setOnClickListener(new leftOnClickListener());
        logout.setOnClickListener(new leftOnClickListener());

        intent = getIntent();
        fragment = intent.getStringExtra("fragment");
        fragmentmagager=this.getFragmentManager();
        FragmentTransaction transaction=fragmentmagager.beginTransaction();
        if(fragment.equals("Bodydata")){
            System.out.println("身体数据设置");
            transaction.replace(R.id.blank,new BodyDataFragment());
            System.out.println("替换成功");
        }else if(fragment.equals("History")){
            System.out.println("运动历史记录");
            transaction.replace(R.id.blank,new HistoryActionFragment());
        }else if(fragment.equals("ActionCount")){
            System.out.println("运动统计");
            transaction.replace(R.id.blank,new ActionCountFragment());
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    class leftOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view == logout){
                System.out.println("退出登录");
                Intent intent = new Intent(LeftViewActivity.this,LoginActivity.class);
                intent.putExtra("fragment","Bodydata");
                startActivity(intent);
            }else{
                Intent intent = new Intent(LeftViewActivity.this,LeftViewActivity.class);
                if (view == bodydatast){
                    System.out.println("身体数据");
                    intent.putExtra("fragment","Bodydata");
                }else if(view == history){
                    System.out.println("历史运动");
                    intent.putExtra("fragment","History");
                }else if(view == count){
                    System.out.println("运动统计");
                    intent.putExtra("fragment","ActionCount");
                }else{

                }
                startActivity(intent);
            }
        }
    }
}
