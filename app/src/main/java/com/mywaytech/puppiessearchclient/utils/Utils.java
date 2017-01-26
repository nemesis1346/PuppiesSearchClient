package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        try {
            //TODO GET CURRENT UTC TIME
//            Calendar calendar = Calendar.getInstance();
//            TimeZone tz = TimeZone.getDefault();
//            calendar.setTimeInMillis((System.currentTimeMillis()/1000) * 1000);
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date currentTimeZone = calendar.getTime();
//            Long  currentTimeZoneLong =currentTimeZone.getTime();
//            return String.valueOf(currentTimeZoneLong);
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            return ts;
        } catch (Exception e) {
        }
        return "";
    }

    public static Date convertLongToDate(String longStringDate) {
        return new Date(Long.valueOf(longStringDate) * 1000);
    }

    public static String convertLongToString(String longStringDate) {
        long timestamp = Long.parseLong(longStringDate) * 1000L;
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date netDate = (new Date(timestamp));
        return sdf.format(netDate);
    }

    public static Bitmap getBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static byte[] getBytesFromUrl(String url) throws IOException {
        URL myUrl = new URL(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = myUrl.openStream();
            byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
            int n;

            while ((n = is.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", myUrl.toExternalForm(), e.getMessage());
            e.printStackTrace();
            // Perform any other exception handling that's appropriate.
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return baos.toByteArray();
    }


    public static String getSpinnerSelection(String spinnerValue) {
        String typeValue = "";
        switch (spinnerValue) {
            case ReportFragment.TYPE_PET_ADOPTION_STRING:
                typeValue = ReportFragment.TYPE_PET_ADOPTION;
                break;
            case ReportFragment.TYPE_PET_LOST_STRING:
                typeValue = ReportFragment.TYPE_PET_LOST;
                break;
            case ReportFragment.TYPE_PET_ALL_STRING:
                typeValue = ReportFragment.TYPE_PET_ALL;
            default:
                typeValue = ReportFragment.TYPE_PET_ALL;
                break;
        }
        return typeValue;
    }

    public static String getNamefromSpinnerSelection(String spinnerValue) {
        String typeValue = "";
        switch (spinnerValue) {
            case ReportFragment.TYPE_PET_ADOPTION:
                typeValue = ReportFragment.TYPE_PET_ADOPTION_STRING;
                break;
            case ReportFragment.TYPE_PET_LOST:
                typeValue = ReportFragment.TYPE_PET_LOST_STRING;
                break;
            default:
                typeValue = ReportFragment.TYPE_PET_ALL;
                break;
        }
        return typeValue;
    }

    public static boolean checkConexion(Context context) {
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || mConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else if (mConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return true;
    }
}
