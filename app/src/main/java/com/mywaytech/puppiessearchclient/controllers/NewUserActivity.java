package com.mywaytech.puppiessearchclient.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.UserDatabase;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewUserActivity extends AppCompatActivity {
    private EditText uName;
    private EditText uEmail;
    private EditText uPassword;
    private Button uSignin;
    private EditText uPassword_repeat;
    private UserDatabase myDB;
    private EditText uAddress;


    private NewUserObject newUserObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = new UserDatabase(this);

        setContentView(R.layout.activity_new_user);
        uName = (EditText) findViewById(R.id.edit_text_name);
        uEmail = (EditText) findViewById(R.id.edit_text_mail);
        uPassword = (EditText) findViewById(R.id.edit_text_password);
        uSignin = (Button) findViewById(R.id.btn_singin);
        uPassword_repeat = (EditText) findViewById(R.id.edit_text_password_repeat);
        uAddress= (EditText) findViewById(R.id.edit_text_address);

        uSignin.setOnClickListener(signInListener);


    }

    public View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //EXTRAS METHOD
            newUserObject = new NewUserObject(uName.getText().toString(), uEmail.getText().toString(), uPassword.getText().toString());

            //PERSISTENCE METHOD
            if (uName.getText().toString().isEmpty() || uEmail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty()||uPassword_repeat.getText().toString().isEmpty()||uAddress.getText().toString().isEmpty()) {
                Toast.makeText(NewUserActivity.this, "No ha ingresado alguno de los campos", Toast.LENGTH_LONG).show();
            } else {
                if (uPassword.getText().toString().equals(uPassword_repeat.getText().toString())) {
                    boolean isInserted = myDB.insertData(uName.getText().toString(),uPassword.getText().toString(), uEmail.getText().toString(),uAddress.getText().toString() );
                    if (isInserted == true) {
                        Toast.makeText(NewUserActivity.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_USERDATA, newUserObject);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NewUserActivity.this, "Problema de registro", Toast.LENGTH_LONG).show();
                        //if it arrives here, means that there was some problem with the registration
                    }
                } else {
                    Toast.makeText(NewUserActivity.this, "Contraseña no coincide", Toast.LENGTH_LONG).show();
                }

            }



        }
    };

}
