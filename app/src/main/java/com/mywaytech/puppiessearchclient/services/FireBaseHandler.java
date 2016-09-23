package com.mywaytech.puppiessearchclient.services;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.models.ReportObject;

/**
 * Created by m.maigua on 7/15/2016.
 */
public class FireBaseHandler {
    private static FireBaseHandler instance = null;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabaseReference;
    private Context mContext;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;

    public static final String OBJECT_USERS_NAME = "USERS";
    public static final String OBJECT_PET_LOST = "PET_FOR_ADOPTION";
    public static final String OBJECT_PET_ADOPTION = "PET_FOR_RESCUE";

    private FireBaseHandler(Context mContext) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = mFirebaseStorage.getReferenceFromUrl("gs://puppiessearch-7c275.appspot.com");
        this.mContext = mContext;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mFirebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        mFirebaseAuth = firebaseAuth;
    }

    public DatabaseReference getFirebaseDatabaseReference() {
        return mFirebaseDatabaseReference;
    }

    public void setFirebaseDatabaseReference(DatabaseReference firebaseDatabaseReference) {
        mFirebaseDatabaseReference = firebaseDatabaseReference;
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
                                Log.d("LoginOnCompleteError: ", " " + e);
                            }
                        });
                        if (callback != null) {
                            Log.d("Success Login: ", "" + task.isSuccessful());
                            callback.onCompleteLogging(task.isSuccessful());

                        }
                    }
                });
    }

    public void fireBaseSignIn(String email, String password, final Activity activity, final CallbackSign callback) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnFailureListener(activity, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("SignOnCompleteError: ", " " + e);
                            }
                        });
                        if (callback != null) {
                            Log.d("Success Sign: ", "" + task.isSuccessful());
                            callback.onCompleteSigning(task.isSuccessful());

                        }
                    }
                });
    }

    public interface CallbackSign {
        void onCompleteSigning(boolean isSigned);
    }

    public interface CallbackLogin {
        void onCompleteLogging(boolean isLogged);
    }


    public boolean saveUserObject(NewUserObject newUserObject) {
        String uid = "";
//        FirebaseUser user= mFirebaseAuth.getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            mFirebaseDatabaseReference.child("users").child(uid).setValue(newUserObject);
            return true;
        } else {
            Log.d("error in creation", "" + "error");
            return false;
        }

    }

    public void savePetObject(ReportObject reportObject, String mType) {
        String uid = "";
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        Log.d("report uname: ",""+reportObject.getuName());
        Log.d("report upath: ",""+reportObject.getImagePath());

        reportObject.setImagePath("asdas");

        if (user != null) {
            uid = reportObject.getuId();
            switch (mType) {
                case ReportFragment.TYPE_PET_LOST:
                    mFirebaseDatabaseReference.child(OBJECT_PET_LOST).child(uid).setValue(reportObject);
                    break;
                case ReportFragment.TYPE_PET_ADOPTION:
                    mFirebaseDatabaseReference.child(OBJECT_PET_ADOPTION).child(uid).setValue(reportObject);
                    break;
            }
            Toast.makeText(mContext, "Se guardó satisfactoriamente el reporte", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("error in creation", "" + "error");
            Toast.makeText(mContext, "Ocurrió un error el reporte", Toast.LENGTH_SHORT).show();
        }
    }

    public StorageReference imageReferenceInFireBase(String imageId) {
        StorageReference mImageRef = mStorageRef.child("images/petImage" + imageId + ".jpg");
        return mImageRef;
    }


    //TODO getcurrentUserObject Method
    // public List<>
}
