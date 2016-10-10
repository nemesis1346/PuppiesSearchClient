package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

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

    public static void openPhone(Context context, String phone) {
        //TODO: maybe check no phone app
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(Intent.createChooser(phoneIntent, context.getString(R.string.select_phone_title)));
    }

    public static void openFacebook(Context context, String url) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse("fb://facewebmodal/f?href=" + url));

        PackageManager packageManager = context.getPackageManager();
        if (facebookIntent.resolveActivity(packageManager) != null) {
            context.startActivity(facebookIntent);
        } else {
            openBrowser(context, url);
        }
    }

    public static void openBrowser(Context context, @NonNull String url) {
        //TODO: maybe check no browser
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(browserIntent, context.getString(R.string.select_browser_title)));
    }


}
