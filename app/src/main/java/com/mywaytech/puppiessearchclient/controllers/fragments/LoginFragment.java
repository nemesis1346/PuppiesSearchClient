package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;
import com.mywaytech.puppiessearchclient.controllers.RegistrationActivity;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;
import com.mywaytech.puppiessearchclient.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Marco on 9/19/2016.
 */
public class LoginFragment extends Fragment implements FireBaseHandler.CallbackLogin {

    private EditText uMail;
    private EditText uPassword;
    private Button bLogin;
    private Button bNewUser;
    private FirebaseAuth mAuth;

    private ProgressBar mProgressBar;
    private Button mRetryBtn;
    private TextView mProgressTextInfo;
    private ImageView mProgressErrorImg;

    private NewUserModel mNewUserObject;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    private ProgressDialogFragment mProgressfragment;

    private DatabaseReference mDatabaseReference;
    private boolean mNotificacionFlag;
    private SignInButton mSignInButtonGoogle;

    private static final String ARG_NOTIFICATION_FLAG = "arg_notification_flag";

    public static final int RC_SIGN_IN = 1346;

    public static LoginFragment newInstance(boolean notificationFlag) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_NOTIFICATION_FLAG, notificationFlag);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificacionFlag = getArguments().getBoolean(ARG_NOTIFICATION_FLAG);
        Log.d("flag not: ", "" + mNotificacionFlag);
        if (mNotificacionFlag) {
            showProgress();
            authenticateUser();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mRetryBtn = (Button) rootView.findViewById(R.id.btn_retry);
        mProgressTextInfo = (TextView) rootView.findViewById(R.id.text_progress_info);
        mProgressErrorImg = (ImageView) rootView.findViewById(R.id.img_error_icon);

        uMail = (EditText) rootView.findViewById(R.id.edit_text_mail_input);
        uPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
        bLogin = (Button) rootView.findViewById(R.id.btn_login);
        bLogin.setOnClickListener(LoginListener);
        bNewUser = (Button) rootView.findViewById(R.id.btn_new_user);
        bNewUser.setOnClickListener(newUserListener);

        //TODO REVIEW GOOGLE SIGN IN
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.server_client_id))
//                .requestEmail()
//                .build();
//        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
//                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mSignInButtonGoogle = (SignInButton) rootView.findViewById(R.id.sign_in_google_button);
        mSignInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);
        mSignInButtonGoogle.setScopes(gso.getScopeArray());
        mSignInButtonGoogle.setOnClickListener(mSignInGoogle);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
    }

    public View.OnClickListener mSignInGoogle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
            mGoogleApiClient.connect();

        }
    };


    public View.OnClickListener LoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //VALIDATION LOGIC
            if (uMail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty()) {
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.login_error_validation_message)
                        .show();
            } else {
                //FIREBASE SIGN METHOD
                showProgress();
                FireBaseHandler.getInstance(getActivity())
                        .fireBaseLogin(uMail.getText().toString(),
                                uPassword.getText().toString(),
                                getActivity(), LoginFragment.this);

            }
        }
    };

    @Override
    public void onCompleteLogging(boolean isLogged) {
        if (isLogged) {
            FireBaseHandler.getInstance(getContext()).getUserObjectFirebaseDatabaseReference()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            NewUserModel mNewUserObject = dataSnapshot.getValue(NewUserModel.class);
                            UserSessionManager.getInstance(getContext()).logged(mNewUserObject, true);

                            hideProgress();
                            new AlertDialogUtils.Builder(getContext())
                                    .setResourceMessage(R.string.login_identified)
                                    .setPositiveText(R.string.btn_ok)
                                    .setIsCancelable(false)
                                    .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = MainActivity.newIntent(getActivity());
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        } else {
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.login_not_identified)
                    .setIsCancelable(false)
                    .setPositiveText(R.string.btn_ok)
                    .show();
        }
    }


    public View.OnClickListener newUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = RegistrationActivity.newIntent(getActivity());
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
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            // mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void showProgress() {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.row_progress, null);
        TextView message = (TextView) customView.findViewById(R.id.text_progress_info);
        message.setText(R.string.login_text);
        mProgressfragment = new ProgressDialogUtils.Builder(getContext())
                .setCustomView(customView)
                .show();

    }

    private void hideProgress() {
        if (mProgressfragment != null && mProgressfragment.isVisible()) {
            mProgressfragment.dismiss();
        }
    }

    private void authenticateUser() {
        FireBaseHandler.getInstance(getContext()).getFirebaseAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    hideProgress();
                    startActivity(MainActivity.newIntent(getContext()));
                    Log.d("signed", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    hideProgress();
                    new AlertDialogUtils.Builder(getContext())
                            .setResourceMessage(R.string.login_not_identified)
                            .setIsCancelable(false)
                            .setPositiveText(R.string.btn_ok)
                            .show();
                    Log.d("logout", "onAuthStateChanged:signed_out");
                }
            }
        });
    }

    private ValueEventListener mSaveUserByGmailCallback = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final NewUserModel googleUserObject = dataSnapshot.getValue(NewUserModel.class);
            FireBaseHandler.getInstance(getContext()).setImageFirebaseStorageReference(googleUserObject.getmUid());
            String mImageFirebasepPath = "userPicture/user" + googleUserObject.getmEmail() + ".jpg";
            final StorageReference mStorageRef = FireBaseHandler.getInstance(getContext()).getUserImageFirebaseStorageReference(mImageFirebasepPath);
            try {

                Picasso.with(getContext())
                        .load(googleUserObject.getmUserImagePath())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                                UploadTask uploadTask = mStorageRef.putBytes(Utils.getBytes(bitmap));
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgress();
                                        new AlertDialogUtils.Builder(getContext())
                                                .setStringMessage(e.getMessage())
                                                .setIsCancelable(false)
                                                .setPositiveText(R.string.btn_ok)
                                                .show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        hideProgress();
                                        UserSessionManager.getInstance(getContext()).saveLocalUser(googleUserObject);
                                        UserSessionManager.getInstance(getContext()).setUserImage(Utils.getBytes(bitmap));
                                        
                                        new AlertDialogUtils.Builder(getContext())
                                                .setResourceMessage(R.string.login_identified)
                                                .setPositiveText(R.string.btn_ok)
                                                .setIsCancelable(false)
                                                .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = MainActivity.newIntent(getActivity());
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();

                                    }
                                });
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {}

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {}
                        });
//
            } catch (Exception e) {
                hideProgress();
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.error_title)
                        .setIsCancelable(false)
                        .setPositiveText(R.string.btn_ok)
                        .show();
                e.printStackTrace();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            showProgress();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            String resultMessage = result.getStatus().getStatusMessage();
            Log.e("resultError:", " " + resultMessage);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information and save it
                NewUserModel googleUserObject = new NewUserModel(
                        acct.getDisplayName(),
                        acct.getEmail(),
                        "",
                        "",
                        acct.getPhotoUrl().toString(),
                        acct.getId());

                FireBaseHandler.getInstance(getContext()).saveUserObjectByGmail(googleUserObject, mSaveUserByGmailCallback);

            } else {
                hideProgress();
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.login_not_identified)
                        .setIsCancelable(false)
                        .setPositiveText(R.string.btn_ok)
                        .show();
            }
        }
    }
}
