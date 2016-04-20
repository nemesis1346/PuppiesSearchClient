package com.mywaytech.puppiessearchclient.controllers;

import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.services.UserDatabase;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText uMail;
    private EditText uPassword;
    private Button bLogin;
    private Button bNewUser;
    private UserDatabase myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new UserDatabase(this);


        uMail = (EditText) findViewById(R.id.edit_text_mail);
        uPassword = (EditText) findViewById(R.id.edit_text_password);
        bLogin = (Button) findViewById(R.id.btn_login);
        bLogin.setOnClickListener(LoginListener);

        bNewUser = (Button) findViewById(R.id.btn_new_user);
        bNewUser.setOnClickListener(newUserListener);


    }

    public View.OnClickListener LoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Boolean res = myDB.getUserdata(uMail.getText().toString());

            //AUTENTIFICATION LOGIC
            if (uMail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "No ha ingresado Usuario o Contraseña", Toast.LENGTH_LONG).show();
            } else {
                if (res) {
                    Toast.makeText(LoginActivity.this, "Usuario Identificado", Toast.LENGTH_LONG).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario no Identificado", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    public View.OnClickListener newUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
            startActivity(intent);
        }
    };


}
