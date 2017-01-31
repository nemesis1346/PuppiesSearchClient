package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
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

    public static Bitmap checkRotationBitmap(String imagePath, Bitmap oldBitmap) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(imagePath);
            int orientation;
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotatedBitmap = oldBitmap;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(oldBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(oldBitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(oldBitmap, 270);
                    break;
                default:
                    rotatedBitmap = oldBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
