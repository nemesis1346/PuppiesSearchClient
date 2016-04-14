package com.mywaytech.puppiessearchclient.controllers.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewUserActivity extends AppCompatActivity {
    private EditText uName;
    private EditText uEmail;
    private EditText uPassword;
    private Button uSignin;
    private Button uLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        uName= (EditText) findViewById(R.id.edit_text_name);
        uEmail= (EditText) findViewById(R.id.edit_text_mail);
        uPassword= (EditText) findViewById(R.id.edit_text_password);
        uSignin= (Button) findViewById(R.id.btn_singin);
        uLogin= (Button) findViewById(R.id.btn_login);

        uSignin.setOnClickListener(signInListener);
        uLogin.setOnClickListener(loginListener);


    }

    public View.OnClickListener signInListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(NewUserActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };
    public View.OnClickListener loginListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(NewUserActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    };
}
