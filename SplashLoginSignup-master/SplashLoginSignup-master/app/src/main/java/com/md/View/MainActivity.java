package com.md.View;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.md.splashloginsignup.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button bodydatast;
    private Button history;
    private Button count;
    private Button logout;
    private DrawerLayout drawerLayout;
    private FragmentManager fm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bodydatast = (Button)findViewById(R.id.bodydataset);
        history = (Button)findViewById(R.id.history);
        count = (Button)findViewById(R.id.count);
        logout = (Button)findViewById(R.id.logout);
        mTextMessage = (TextView) findViewById(R.id.message);
        fm = getSupportFragmentManager();
    }

    public void  bodydatast(){
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(R.id.content, new Fragment());
        bt.commit();
    }
    public void history(){
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(R.id.content, new Fragment());
        bt.commit();
    }
    public void count(){
        FragmentTransaction bt = fm.beginTransaction();
        bt.replace(R.id.content, new Fragment());
        bt.commit();
    }
    public void logout(){
        startActivity(new Intent(this, SplashActivity.class));
    }

}
