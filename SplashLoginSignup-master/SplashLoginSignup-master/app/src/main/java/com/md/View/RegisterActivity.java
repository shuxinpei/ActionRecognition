package com.md.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.md.splashloginsignup.R;
import com.md.splashloginsignup.databinding.ActivityLoginBinding;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

public class RegisterActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    EditText et_email_address;
    EditText et_password;
    EditText Repeated_password;
    Button btn_signup;
    ImageButton back;

    String res="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        et_email_address =(EditText)findViewById(R.id.et_email_address);
        et_password =(EditText)findViewById(R.id.et_password);
        Repeated_password = (EditText)findViewById(R.id.et_repeatpassword);
        btn_signup =(Button )findViewById(R.id.btn_signup);
        back = (ImageButton)findViewById(R.id.back);
    }

    public void signup(View view)throws Exception{
        String username = et_email_address.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repeatedpassword = Repeated_password.getText().toString().trim();
        startActivity(new Intent(this, MainActivity.class));
        System.out.print("跳转");

    }
    public void back(){
        startActivity(new Intent(this, LoginActivity.class));
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
