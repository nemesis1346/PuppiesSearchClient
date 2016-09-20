package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.LoginActivity;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;
import com.mywaytech.puppiessearchclient.controllers.RegistrationActivity;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

/**
 * Created by Marco on 9/19/2016.
 */
public class LoginFragment extends Fragment implements FireBaseHandler.CallbackLogin{

    private EditText uMail;
    private EditText uPassword;
    private Button bLogin;
    private Button bNewUser;
    private FirebaseAuth mAuth;

    public static LoginFragment newInstance(){
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        uMail = (EditText) rootView.findViewById(R.id.edit_text_mail_input);
        uPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
        bLogin = (Button) rootView.findViewById(R.id.btn_login);
        bLogin.setOnClickListener(LoginListener);
        bNewUser = (Button) rootView.findViewById(R.id.btn_new_user);
        bNewUser.setOnClickListener(newUserListener);
        return rootView;
    }


    public View.OnClickListener LoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //VALIDATION LOGIC
            if (uMail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.login_error_validation_message, Toast.LENGTH_LONG).show();
            } else {
                //FIREBASE SIGN METHOD
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
            Intent intent = MainActivity.newIntent(getActivity());
            Toast.makeText(getActivity(), R.string.login_identified, Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(),R.string.login_error_noidentified, Toast.LENGTH_LONG).show();
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
}
