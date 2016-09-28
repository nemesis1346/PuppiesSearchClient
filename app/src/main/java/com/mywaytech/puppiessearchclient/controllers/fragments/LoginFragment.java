package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;
import com.mywaytech.puppiessearchclient.controllers.RegistrationActivity;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;

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

    private NewUserObject mNewUserObject;

    private ProgressDialogFragment mProgressfragment;

    private DatabaseReference mDatabaseReference;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
    }

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
                            NewUserObject mNewUserObject = dataSnapshot.getValue(NewUserObject.class);
                            UserSessionManager.getInstance(getContext()).saveLocalUser(mNewUserObject);
                            hideProgress();
                            new AlertDialogUtils.Builder(getContext())
                                    .setResourceMessage(R.string.login_identified)
                                    .setPositiveText(R.string.btn_ok)
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

}
