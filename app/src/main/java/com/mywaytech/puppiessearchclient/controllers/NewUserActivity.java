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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.services.UserDatabase;

import java.util.UUID;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewUserActivity extends AppCompatActivity implements FireBaseHandler.CallbackSign {
    private EditText uName;
    private EditText uEmail;
    private EditText uPassword;
    private Button uSignin;
    private EditText uPassword_repeat;
    private UserDatabase myDB;
    private EditText uAddress;

    //private FirebaseAuth.AuthStateListener mAuthListener;

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
                Toast.makeText(NewUserActivity.this, "No ha ingresado alguno de los campos", Toast.LENGTH_LONG).show();
            } else {
                if (uPassword.getText().toString().equals(uPassword_repeat.getText().toString())) {
                    //TODO FIRST, VALIDATE THE EXISTENCE OF CURRENT USER
                    //FIREBASE INTEND
                    FireBaseHandler.getInstance(NewUserActivity.this)
                            .fireBaseSignIn(uEmail.getText().toString(), uPassword.getText().toString(), NewUserActivity.this, NewUserActivity.this);
                } else {
                    Toast.makeText(NewUserActivity.this, "Contraseña no coincide", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    @Override
    public void onCompleteSigning(boolean isSigned) {
        boolean isSaved=false;
        if (isSigned) {
            //GET CURRENT USER INFO
            isSaved=FireBaseHandler.getInstance(NewUserActivity.this).saveUserObject(newUserObject);
            if(isSaved){
                Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                Toast.makeText(NewUserActivity.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                intent.putExtra(MainActivity.EXTRA_EMAIL_FORAUTH, myDB.getEmail(uEmail.getText().toString()));
                startActivity(intent);
            }else{
                Toast.makeText(NewUserActivity.this, "Error al Registrar", Toast.LENGTH_LONG).show();
            }
        } else {
            isSaved=false;
            Toast.makeText(NewUserActivity.this, "Usuario Existente", Toast.LENGTH_LONG).show();
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
