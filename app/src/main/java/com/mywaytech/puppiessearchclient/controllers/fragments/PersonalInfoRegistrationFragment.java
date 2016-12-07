package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ValidationUtils;
import com.mywaytech.puppiessearchclient.widgets.CustomEditText;

/**
 * Created by Marco on 21/9/2016.
 */
public class PersonalInfoRegistrationFragment extends RegistrationBaseFragment {

    private EditText uName;
    private EditText uEmail;
    private CustomEditText uPassword;
    private Button uSignin;
    private CustomEditText uPassword_repeat;
    private EditText uAddress;

    private NewUserModel mNewUserObject;

    public static final String ARG_NEW_USER_OBJECT = "arg_new_wer_object";

    public static PersonalInfoRegistrationFragment newInstance(NewUserModel newUserObject) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEW_USER_OBJECT, newUserObject);
        PersonalInfoRegistrationFragment fragment = new PersonalInfoRegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewUserObject = (NewUserModel) getArguments().getSerializable(ARG_NEW_USER_OBJECT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info_registration, container, false);
        uName = (EditText) rootView.findViewById(R.id.edit_text_name);
        uEmail = (EditText) rootView.findViewById(R.id.edit_text_mail);
        uPassword = (CustomEditText) rootView.findViewById(R.id.edit_text_password);
        uPassword_repeat = (CustomEditText) rootView.findViewById(R.id.edit_text_password_repeat);
        uAddress = (EditText) rootView.findViewById(R.id.edit_text_address);

        //TODO LOCAL UPDATE OF THE OBJECT

        if (mNewUserObject != null) {
            if (mNewUserObject.getmName() != null)
                uName.setText(mNewUserObject.getmName());
            if (mNewUserObject.getmName() != null)
                uEmail.setText(mNewUserObject.getmEmail());
            if (mNewUserObject.getmPassword() != null) {
                uPassword.setText(mNewUserObject.getmPassword());
                uPassword_repeat.setText(mNewUserObject.getmPassword());
            }
            if (mNewUserObject.getmAddress() != null) {
                uAddress.setText(mNewUserObject.getmAddress());
            }
        }
        return rootView;
    }

    @Override
    public boolean isFormReady() {
        if (uName.getText().toString().isEmpty()
                || uEmail.getText().toString().isEmpty() ||
                uPassword.getText().toString().isEmpty() ||
                uPassword_repeat.getText().toString().isEmpty() ||
                uAddress.getText().toString().isEmpty()) {

            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.message_validation_error)
                    .setPositiveText(R.string.btn_ok)
                    .setTitle(R.string.error_title)
                    .show();

            return false;

        } else if (ValidationUtils.isValidEmail(uEmail.getText().toString()) == R.string.error_invalid_user_email) {
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.error_invalid_user_email)
                    .setPositiveText(R.string.btn_ok)
                    .setTitle(R.string.error_title)
                    .show();

            return false;
        } else if (uPassword.getText().toString().equals(uPassword_repeat.getText().toString())) {

            mNewUserObject.setmName(uName.getText().toString());
            mNewUserObject.setmEmail(uEmail.getText().toString());
            mNewUserObject.setmPassword(uPassword.getText().toString());
            mNewUserObject.setAddress(uAddress.getText().toString());
            mPersonalInfoRegistrationCallback.updateRegistrationModel(mNewUserObject);
            return true;
        } else {

            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.message_validation_password)
                    .setPositiveText(R.string.btn_ok)
                    .setTitle(R.string.error_title)
                    .show();

            return false;
        }
    }


}

