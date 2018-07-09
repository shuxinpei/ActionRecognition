package com.md.View;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.md.View.fragments.FreeActionFragment;
import com.md.View.fragments.MainFragment;
import com.md.View.fragments.SpecifyActionFragment;
import com.md.splashloginsignup.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FreeActionFragment.OnFragmentInteractionListener, SpecifyActionFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private Button bodydatast;
    private Button history;
    private Button count;
    private Button logout;
    private DrawerLayout drawerLayout;

    private TextView FreeAction;
    private TextView SpecifyAction;
    private ViewPager mMViewPager;
    //管理的布局
    List<Fragment> mFragments = new ArrayList<Fragment>() ;

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
        //需要改变样式的文本
        FreeAction = (TextView)findViewById(R.id.FreeAction);
        SpecifyAction = (TextView)findViewById(R.id.SpecifyAction);

        //中间需要进行替换的布局
        mFragments.add(new FreeActionFragment());
        mFragments.add(new SpecifyActionFragment());

        mMViewPager = (ViewPager) findViewById(R.id.content);
        mMViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

  /*  public void  bodydatast(){
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
    }*/
    public void logout(){
        startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    //改变样式
    private void changeTabStyle(Fragment tabFragment) {
        if (tabFragment instanceof FreeActionFragment) {
            FreeAction.setTextColor(Color.parseColor("#45C01A"));
        }
        if (tabFragment instanceof SpecifyActionFragment) {
            SpecifyAction.setTextColor(Color.parseColor("#45C01A"));
        }
    }
    //清除样式
    private void clearSeleted() {
         FreeAction.setTextColor(Color.parseColor("#999999"));
         SpecifyAction.setTextColor(Color.parseColor("#999999"));
    }
    //进行类的管理
    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f =  mFragments.get(position);
            clearSeleted();
            changeTabStyle(f);
            return f;
        }
//可以考虑将最上面的东西直接加在conten里面，设置好样式
        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
