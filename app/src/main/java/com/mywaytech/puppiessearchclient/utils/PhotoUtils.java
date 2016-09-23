package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Marco on 25/8/2016.
 */
public class PhotoUtils {

    public static byte[] processImagePet(Bitmap imageBitmap) {
        //TODO create an unique name for the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;

    }

    //FIXME THIS, MIGHT BE ERASED
    public static String imageProcessed_option_for_string(Bitmap photo) {
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        photo.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }

    public static File setPhotoFile(Context context) {
        File file = new File(context.getExternalFilesDir(null) + "/images");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        file = new File(context.getExternalFilesDir(null) + "/images", "img_" + System.currentTimeMillis() + ".jpg");
        return file;
    }

    //FIXME MIGHT BE ERASED
    public static Bitmap photoResultProcessing(Context context, Bitmap photo, String filePath) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return photo;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setImageResultFromFirebase() {

    }

}
