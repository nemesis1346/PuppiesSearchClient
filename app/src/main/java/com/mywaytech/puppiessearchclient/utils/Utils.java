package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.content.Intent;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by Marco on 28/9/2016.
 */
public class Utils {
    public static void openEmail(Context context, String[] emails, String subject, String message) {
        //TODO: maybe check no email client
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emails == null ? new String[]{} : emails);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject == null ? "" : subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message == null ? "" : message);

        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.select_email_title)));
    }

}
