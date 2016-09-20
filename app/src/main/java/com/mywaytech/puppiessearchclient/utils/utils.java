package com.mywaytech.puppiessearchclient.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Marco on 25/8/2016.
 */
public class Utils {

    public static byte[] processImagePet(Bitmap imageBitmap){
        //TODO create an unique name for the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;

    }

    public static String imageProcessed_option_for_string(Bitmap photo){
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        photo.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }


}
