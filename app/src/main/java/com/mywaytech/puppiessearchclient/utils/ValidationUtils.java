package com.mywaytech.puppiessearchclient.utils;

import android.support.annotation.StringRes;

import com.mywaytech.puppiessearchclient.R;

import java.util.regex.Pattern;

/**
 * Created by nemesis1346 on 5/12/2016.
 */

public class ValidationUtils {

    public static final int NO_ERROR = 0;
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=[.\\S]*[A-Z])(?=[.\\S]*\\d)([.\\S]{8,20})$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");

    @StringRes
    public static int isValidEmail(String email) {
        int errorMessage = NO_ERROR;
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            errorMessage = R.string.error_invalid_user_email;
        }
        return errorMessage;
    }

    @StringRes
    public static int isValidPassword(String password) {
        int errorMessage = NO_ERROR;
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errorMessage = R.string.error_invalid_password;
        }
        return errorMessage;
    }
}
