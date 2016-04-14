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
public class LoginActivity extends AppCompatActivity {
    private EditText uMail;
    private EditText uPassword;
    private Button bSignin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uMail= (EditText) findViewById(R.id.edit_text_mail);
        uPassword= (EditText) findViewById(R.id.edit_text_password);
        bSignin= (Button) findViewById(R.id.btn_login);
        bSignin.setOnClickListener(signListener);

    }

    public View.OnClickListener signListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };
}
