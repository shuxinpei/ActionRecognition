package com.md.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.md.View.fragments.shenaFragment;
import com.md.View.fragments.xiadunFragment;
import com.md.splashloginsignup.R;

public class StartSpecifyActionActivity extends AppCompatActivity implements shenaFragment.OnFragmentInteractionListener,xiadunFragment.OnFragmentInteractionListener {
    String fragment;
    Intent intent;
    FragmentManager fragmentmagager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_specify_action);
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
