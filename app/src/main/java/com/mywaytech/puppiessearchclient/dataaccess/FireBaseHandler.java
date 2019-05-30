package com.mywaytech.puppiessearchclient.dataaccess;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.models.ReportModel;
import com.mywaytech.puppiessearchclient.utils.Utils;

/**
 * Created by m.maigua on 7/15/2016.
 */
public class FireBaseHandler {

    private static FireBaseHandler instance = null;
    private Context mContext;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;

    public static final String OBJECT_USERS_NAME = "USERS";
    public static final String REPORTS = "REPORTS";


    private FireBaseHandler(Context context) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = mFirebaseStorage.getReferenceFromUrl("gs://puppies-client.appspot.com");
        this.mContext = context;
    }

    public StorageReference getmStorageRef() {
        return mStorageRef;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mFirebaseAuth;
    }

    public DatabaseReference getFirebaseDatabaseReference() {
        return mFirebaseDatabaseReference;
    }

    public synchronized static FireBaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new FireBaseHandler(context);
        }
        return instance;
    }

    public void fireBaseLogin(String email, String password, final Activity activity, final CallbackLogin callback) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.addOnFailureListener(activity, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("LoginOnCompleteError: ", " " + e);
                                callback.onFailLogging(e);
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
                                callback.onFailSigningUp(e);
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
        void onFailSigningUp(Exception e);

        void onCompleteSigning(boolean isSigned);
    }

    public interface CallbackLogin {
        void onFailLogging(Exception e);

        void onCompleteLogging(boolean isLogged);
    }


    public void editUserObject(NewUserModel newUserModel) {

    }

    public void saveUserObjectByGmail(NewUserModel newUserModel, ValueEventListener mCallback) {
        mFirebaseDatabaseReference.child(OBJECT_USERS_NAME).child(newUserModel.getmUid()).setValue(newUserModel);
        mFirebaseDatabaseReference.child(FireBaseHandler.OBJECT_USERS_NAME).child(newUserModel.getmUid())
                .addListenerForSingleValueEvent(mCallback);
    }

    public void saveUserObject(NewUserModel newUserObject) {
        String uid = "";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            newUserObject.setUid(user.getUid());
            mFirebaseDatabaseReference.child(OBJECT_USERS_NAME).child(uid).setValue(newUserObject);

            mFirebaseDatabaseReference.child(FireBaseHandler.OBJECT_USERS_NAME).child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            NewUserModel mNewUserObject = dataSnapshot.getValue(NewUserModel.class);

                            final long ONE_MEGABYTE = 1024 * 1024;

                            FireBaseHandler.getInstance(mContext)
                                    .getUserImageFirebaseStorageReference(mNewUserObject.getmUserImagePath())
                                    .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    UserSessionManager.getInstance(mContext).setUserImage(bytes);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.e("errorSaveImage: ", exception.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        } else {
            Log.d("error in creation", "" + "error");

        }

    }

    public boolean savePetObject(ReportModel reportModel) {
        mFirebaseDatabaseReference.child(REPORTS).child(reportModel.getuId()).setValue(reportModel, ServerValue.TIMESTAMP);
        return true;
    }

    public StorageReference setImageFirebaseStorageReference(String imageId) {
        return mStorageRef.child("images/petImage" + imageId + ".jpg");
    }

    public StorageReference getImageFirebaseStorageReference(String imagePath) {
        return mStorageRef.child(imagePath);
    }

    public StorageReference getUserImageFirebaseStorageReference(String imagePath) {
        return mStorageRef.child(imagePath);
    }

    public DatabaseReference getUserObjectFirebaseDatabaseReference() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        String uid = user.getUid();
        return mFirebaseDatabaseReference.child(FireBaseHandler.OBJECT_USERS_NAME).child(uid);
    }

    public DatabaseReference getUserObjectFirebaseDatabaseReferenceRaw() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        return mFirebaseDatabaseReference.child(FireBaseHandler.OBJECT_USERS_NAME);
    }

    public Query getReportsFirebaseDatabaseReferenceByDate() {
        return mFirebaseDatabaseReference.child(REPORTS).orderByChild("uDate");
    }

    public DatabaseReference getReportsFirebaseDatabaseReferenceBySort() {
        return mFirebaseDatabaseReference.child(REPORTS);
    }

    public String getUserKey() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        return user.getUid();
    }

}
