package com.mywaytech.puppiessearchclient.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewPetActivity extends BaseActivity {
    private EditText newAddress;
    private EditText newComment;
    private EditText newResponsable;
    private int newImage;
    static int TAKE_PICTURE_PHOTO = 3;
    private static final int CAMERA_REQUEST = 1888;
    private static final int BACK_TOACTIVITY = 1889;
    private Button btn_image;
    private Button btn_report;

    private Bitmap photo;
    private UserPetObject userPetObject;

    @Override
    public int getToolbarTitle() {
        return R.string.resport_lost_pet;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_new_lost_pet;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newAddress = (EditText) findViewById(R.id.edit_text_address);
        newComment = (EditText) findViewById(R.id.edit_text_password);
        newResponsable = (EditText) findViewById(R.id.edit_text_name);
        btn_image = (Button) findViewById(R.id.input_image);
        btn_report = (Button) findViewById(R.id.btn_reportar);

        btn_image.setOnClickListener(addPhoto);
        btn_report.setOnClickListener(addPhoto);
    }

    public View.OnClickListener addPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File file;
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            btn_image.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn_image.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            photo = (Bitmap) data.getExtras().get("data");

            file=new File(this.getExternalFilesDir(null)+"/images");
            if(!file.isDirectory()){
                file.mkdir();
            }
            file=new File(this.getExternalFilesDir(null)+"/images","img_" + System.currentTimeMillis()+".jpg");
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode ==  BACK_TOACTIVITY && resultCode == Activity.RESULT_OK) {
            userPetObject=new UserPetObject(newResponsable.getText().toString(),newAddress.getText().toString(), file.getPath().toString());
            data.putExtra();
        }

    }

    public View.OnClickListener backToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(NewPetActivity.this, MainActivity.class);
            //intent.putExtra(MainActivity.EXTRA_NEWPET_DATA, userPetObject);
            startActivityForResult(intent,BACK_TOACTIVITY);
        }
    };
}
