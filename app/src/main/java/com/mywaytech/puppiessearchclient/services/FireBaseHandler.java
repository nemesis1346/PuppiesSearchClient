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
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.maigua on 7/15/2016.
 */
public class FireBaseHandler{
    private static FireBaseHandler instance = null;
    private FirebaseAuth mFirebaseAuth;
    private Context mContext;


    private FireBaseHandler(Context mContext) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        this.mContext = mContext;
    }

    public synchronized static FireBaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new FireBaseHandler(context);
        }
        return instance;
    }

    //TODO FIX THIS AS THE FIREBASE SIGNED
    public boolean fireBaseLogin(String email, String password, Activity activity) {
        final List<String> isValidArray=new ArrayList<>();
        //mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    isValidArray.add("true");
                    Log.w("Signing Error", "signInWithEmail", task.getException());
                } else {
                    isValidArray.add("false");
                    Log.d("Signed Sucessfully", "signInWithEmail:onComplete:" + task.isSuccessful());
                }
            }
        });
        if(isValidArray.get(0).equals("true")){
            return true;
        }else{
            return false;
        }
    }

    public void fireBaseSignIn(String email, String password, final Activity activity, final Callback callback){
        //mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                task.addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("onCompleteError: "," "+e);
                    }
                });
                if(callback!=null){
                    Log.d("success: ",""+task.isSuccessful());
                    callback.onCompleteSigning(task.isSuccessful());

                }
            }
        });
    }
    public interface Callback{
        void onCompleteSigning(boolean isSigned);
    }
}
