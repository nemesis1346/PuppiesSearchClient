package com.mywaytech.puppiessearchclient.services;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mywaytech.puppiessearchclient.models.NewUserObject;

/**
 * Created by m.maigua on 7/15/2016.
 */
public class FireBaseHandler{
    private static FireBaseHandler instance = null;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    private Context mContext;


    private FireBaseHandler(Context mContext) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase= FirebaseDatabase.getInstance().getReference();

        this.mContext = mContext;
    }

    public synchronized static FireBaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new FireBaseHandler(context);
        }
        return instance;
    }

    //TODO FIX THIS AS THE FIREBASE SIGNED
    public void fireBaseLogin(String email, String password, final Activity activity, final CallbackLogin callback) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("LoginOnCompleteError: "," "+e);
                    }
                });
                if(callback!=null){
                    Log.d("Success Login: ",""+task.isSuccessful());
                    callback.onCompleteLogging(task.isSuccessful());

                }
            }
        });
    }

    public void fireBaseSignIn(String email, String password, final Activity activity, final CallbackSign callback){
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("SignOnCompleteError: "," "+e);
                    }
                });
                if(callback!=null){
                    Log.d("Success Sign: ",""+task.isSuccessful());
                    callback.onCompleteSigning(task.isSuccessful());

                }
            }
        });
    }
    public interface CallbackSign{
        void onCompleteSigning(boolean isSigned);
    }
    public interface CallbackLogin{
        void onCompleteLogging(boolean isLogged);
    }


    public boolean saveUserObject(NewUserObject newUserObject){
        String uid="";
        FirebaseUser user= mFirebaseAuth.getCurrentUser();
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            mFirebaseDatabase.child("users").child(uid).setValue(newUserObject);
            return true;
        }else{
            Log.d("error in creation", ""+"error");
            return false;
        }

    }

    //TODO getcurrentUserObject Method
}
