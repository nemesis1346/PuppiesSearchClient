package com.mywaytech.puppiessearchclient.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class LoginActivity extends AppCompatActivity implements FireBaseHandler.CallbackLogin {
    private EditText uMail;
    private EditText uPassword;
    private Button bLogin;
    private Button bNewUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uMail = (EditText) findViewById(R.id.edit_text_mail_input);
        uPassword = (EditText) findViewById(R.id.edit_text_password);
        bLogin = (Button) findViewById(R.id.btn_login);
        bLogin.setOnClickListener(LoginListener);
        bNewUser = (Button) findViewById(R.id.btn_new_user);
        bNewUser.setOnClickListener(newUserListener);
    }

    public View.OnClickListener LoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //VALIDATION LOGIC
            if (uMail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "No ha ingresado Usuario o Contraseña", Toast.LENGTH_LONG).show();
            } else {
                //FIREBASE SIGN METHOD
                FireBaseHandler.getInstance(LoginActivity.this)
                        .fireBaseLogin(uMail.getText().toString(), uPassword.getText().toString(), LoginActivity.this, LoginActivity.this);
            }
        }
    };

    @Override
    public void onCompleteLogging(boolean isLogged) {
        if (isLogged) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Toast.makeText(LoginActivity.this, "Usuario Identificado", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Usuario no Identificado", Toast.LENGTH_LONG).show();
        }
    }

    public View.OnClickListener newUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    };

    //TODO AFTERWORDS LOOK FOR A FIX TO THIS CODE, IT SHOULD BE ON THE FIREBASE HANDLER

    public FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d("signed", "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d("logout", "onAuthStateChanged:signed_out");
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            // mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
