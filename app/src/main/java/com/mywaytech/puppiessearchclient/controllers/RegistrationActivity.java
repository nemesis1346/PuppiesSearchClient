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
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class RegistrationActivity extends AppCompatActivity implements FireBaseHandler.CallbackSign {
    private EditText uName;
    private EditText uEmail;
    private EditText uPassword;
    private Button uSignin;
    private EditText uPassword_repeat;
    private EditText uAddress;

    //private FirebaseAuth.AuthStateListener mAuthListener;

    private NewUserObject newUserObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_registration);
        uName = (EditText) findViewById(R.id.edit_text_name);
        uEmail = (EditText) findViewById(R.id.edit_text_mail);
        uPassword = (EditText) findViewById(R.id.edit_text_password);
        uSignin = (Button) findViewById(R.id.btn_singin);
        uPassword_repeat = (EditText) findViewById(R.id.edit_text_password_repeat);
        uAddress = (EditText) findViewById(R.id.edit_text_address);

        uSignin.setOnClickListener(signInListener);
    }

    public View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //EXTRAS METHOD
            newUserObject = new NewUserObject(uName.getText().toString(), uEmail.getText().toString(), uPassword.getText().toString());

            //PERSISTENCE METHOD
            if (uName.getText().toString().isEmpty() || uEmail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty() || uPassword_repeat.getText().toString().isEmpty() || uAddress.getText().toString().isEmpty()) {
                Toast.makeText(RegistrationActivity.this, "No ha ingresado alguno de los campos", Toast.LENGTH_LONG).show();
            } else {
                if (uPassword.getText().toString().equals(uPassword_repeat.getText().toString())) {
                    //TODO FIRST, VALIDATE THE EXISTENCE OF CURRENT USER
                    //FIREBASE INTEND
                    FireBaseHandler.getInstance(RegistrationActivity.this)
                            .fireBaseSignIn(uEmail.getText().toString(), uPassword.getText().toString(), RegistrationActivity.this, RegistrationActivity.this);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Contraseña no coincide", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    @Override
    public void onCompleteSigning(boolean isCreated) {
        boolean isSaved;
        if (isCreated) {
            //GET CURRENT USER INFO
            isSaved=FireBaseHandler.getInstance(RegistrationActivity.this).saveUserObject(newUserObject);
            if(isSaved){
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                Toast.makeText(RegistrationActivity.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }else{
                Toast.makeText(RegistrationActivity.this, "Error al Registrar", Toast.LENGTH_LONG).show();
            }
        } else {
            isSaved=false;
            Toast.makeText(RegistrationActivity.this, "Email no Existente", Toast.LENGTH_LONG).show();
        }
    }





//    public FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            //this is what i need
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if (user != null) {
//                Log.d("signed", "onAuthStateChanged:signed_in:" + user.getUid());
//            } else {
//                Log.d("logout", "onAuthStateChanged:signed_out");
//            }
//        }
//    };

//    public ValueEventListener fireListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            IdFirebaseUser = dataSnapshot.getValue().toString();
//            //String value=dataSnapshot.getValue().toString();
//            Log.d("ValueChanged", "Value is: " + IdFirebaseUser);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Log.w("ERROR", "Failed to read value: " + databaseError.toException());
//        }
//    };


//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }

}
