package com.md.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.md.splashloginsignup.R;
import com.md.splashloginsignup.databinding.ActivityLoginBinding;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import Util.test;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    EditText et_email_address;
    EditText et_password;

    String res="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        et_email_address =(EditText)findViewById(R.id.et_email_address);
        et_password =(EditText)findViewById(R.id.et_password);


    }
    public void Login(View view)throws Exception{
        startActivity(new Intent(this, MainActivity.class));
        System.out.print("跳转");
    }

    public void signup(View view)throws Exception{
        startActivity(new Intent(this, RegisterActivity.class));
        System.out.print("跳转");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
