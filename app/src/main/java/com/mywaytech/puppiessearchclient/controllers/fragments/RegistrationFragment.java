package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;
import com.mywaytech.puppiessearchclient.controllers.UserPictureRegistrationActivity;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;

/**
 * Created by Marco on 21/9/2016.
 */
public class RegistrationFragment extends Fragment implements FireBaseHandler.CallbackSign{
    private EditText uName;
    private EditText uEmail;
    private EditText uPassword;
    private Button uSignin;
    private EditText uPassword_repeat;
    private EditText uAddress;

    //private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialogFragment mProgressfragment;

    private NewUserObject newUserObject;

    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        uName = (EditText) rootView.findViewById(R.id.edit_text_name);
        uEmail = (EditText) rootView.findViewById(R.id.edit_text_mail);
        uPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
        uSignin = (Button) rootView.findViewById(R.id.btn_singin);
        uPassword_repeat = (EditText) rootView.findViewById(R.id.edit_text_password_repeat);
        uAddress = (EditText) rootView.findViewById(R.id.edit_text_address);

        uSignin.setOnClickListener(signInListener);
        return rootView;
    }
    public View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //EXTRAS METHOD
            newUserObject = new NewUserObject(uName.getText().toString(), uEmail.getText().toString(), uPassword.getText().toString());

            if (uName.getText().toString().isEmpty() || uEmail.getText().toString().isEmpty() || uPassword.getText().toString().isEmpty() || uPassword_repeat.getText().toString().isEmpty() || uAddress.getText().toString().isEmpty()) {
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.message_validation_error)
                        .setPositiveText(R.string.btn_ok)
                        .setTitle(R.string.error_title)
                        .show();
            } else {
                if (uPassword.getText().toString().equals(uPassword_repeat.getText().toString())) {
                    //TODO FIRST, VALIDATE THE EXISTENCE OF CURRENT USER
                    //FIREBASE INTEND
                    showProgress();
                    FireBaseHandler.getInstance(getContext())
                            .fireBaseSignIn(uEmail.getText().toString(), uPassword.getText().toString(), getActivity(), RegistrationFragment.this);
                } else {
                    new AlertDialogUtils.Builder(getContext())
                            .setResourceMessage(R.string.message_validation_password)
                            .setPositiveText(R.string.btn_ok)
                            .setTitle(R.string.error_title)
                            .show();
                }
            }
        }
    };

    @Override
    public void onCompleteSigning(boolean isCreated) {
        boolean isSaved;
        if (isCreated) {
            //GET CURRENT USER INFO
            isSaved=FireBaseHandler.getInstance(getContext()).saveUserObject(newUserObject);
            if(isSaved){
                hideProgress();
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.registration_success)
                        .setPositiveText(R.string.btn_ok)
                        .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = UserPictureRegistrationActivity.newIntent(getContext());
                                startActivity(intent);
                            }
                        })
                        .show();

            }else{
                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.registration_failure)
                        .setPositiveText(R.string.btn_ok)
                        .show();
            }
        } else {
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.registration_failure)
                    .setPositiveText(R.string.btn_ok)
                    .show();
        }
    }

    private void showProgress() {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.row_progress, null);
        TextView message = (TextView) customView.findViewById(R.id.text_progress_info);
        message.setText(R.string.registration_text);
        mProgressfragment = new ProgressDialogUtils.Builder(getContext())
                .setCustomView(customView)
                .show();

    }

    private void hideProgress() {
        if(mProgressfragment!=null && mProgressfragment.isVisible()){
            mProgressfragment.dismiss();
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


}

