package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import com.mywaytech.puppiessearchclient.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    public static String getCurrentDateTime() {
        try{
            //TODO GET CURRENT UTC TIME
//            Calendar calendar = Calendar.getInstance();
//            TimeZone tz = TimeZone.getDefault();
//            calendar.setTimeInMillis((System.currentTimeMillis()/1000) * 1000);
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date currentTimeZone = calendar.getTime();
//            Long  currentTimeZoneLong =currentTimeZone.getTime();
//            return String.valueOf(currentTimeZoneLong);
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            return ts;
        }catch (Exception e) {
        }
        return "";
    }

    public static Date convertLongToDate(String longStringDate) {
        return new Date(Long.valueOf(longStringDate) * 1000);
    }

    public static String convertLongToString(String longStringDate) {
        // Create a DateFormatter object for displaying date in specified format.
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(longStringDate));
        return formatter.format(calendar.getTime());
    }

    public static Bitmap getBitmap(VectorDrawableCompat vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
