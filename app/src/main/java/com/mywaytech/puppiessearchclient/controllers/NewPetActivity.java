package com.mywaytech.puppiessearchclient.controllers;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewPetActivity extends BaseActivity {
    private EditText newAddress;
    private EditText newComment;
    private int newImage;
    private ImageButton addImageButton;
    static int TAKE_PICTURE_PHOTO = 3;

    @Override
    public int getToolbarTitle() {
        return R.string.resport_lost_pet;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_new_lost_pet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        newAddress= (EditText) findViewById(R.id.edit_text_address);
        newComment= (EditText) findViewById(R.id.edit_text_password);
        addImageButton= (ImageButton) findViewById(R.id.check_add_button);
        addImageButton.setOnClickListener(addPhoto);
    }

public View.OnClickListener addPhoto=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_PHOTO);
    }
};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_PHOTO && resultCode== Activity.RESULT_OK){
            addImageButton.setImageResource(R.drawable.ic_check);
        }
    }
}
