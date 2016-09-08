package com.mywaytech.puppiessearchclient.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AdoptionFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.WallFragment;
import com.mywaytech.puppiessearchclient.models.UserPetObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewPetActivity extends BaseActivity {
    private EditText newAddress;
    private EditText newComment;
    private static final int CAMERA_REQUEST = 0;
    private Button btn_image;
    private Button btn_report;

    private ImageView imageShow;

    public static final String FRAGMENT_VALUE = "com.mywaytech.puppiessearchclient.extras.extra_fragment_value";

    private Bitmap photo;
    private UserPetObject userPetObject;

    private File file;
    private int callback;
    private String final_path;

    private FirebaseUser mCurrentUser;
    private String mCurrentUserName;

    private FirebaseAuth mFirebaseAuth;

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

        callback = getIntent().getIntExtra(FRAGMENT_VALUE, 0);

        newAddress = (EditText) findViewById(R.id.edit_text_address_lost_activity);
        newComment = (EditText) findViewById(R.id.edit_text_comment_lost_activity);

        btn_image = (Button) findViewById(R.id.input_image);
        btn_report = (Button) findViewById(R.id.btn_reportar);

        imageShow = (ImageView) findViewById(R.id.showImage);

        btn_image.setOnClickListener(addPhoto);
        btn_report.setOnClickListener(backToActivity);


        mFirebaseAuth = FireBaseHandler.getInstance(this).getFirebaseAuth();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        mCurrentUser = FireBaseHandler.getInstance(this).getFirebaseAuth().getCurrentUser();
//        if(mCurrentUser!=null){
//            for (UserInfo profile : mCurrentUser.getProviderData()) {
////                mCurrentUserName = mCurrentUser.getDisplayName();
//                mCurrentUserName=profile.getDisplayName();
//            }
//        }else{
//            Toast.makeText(NewPetActivity.this, "Usuario Sin No ha iniciado sesión", Toast.LENGTH_LONG).show();
//            finish();
//        }
    }

    public View.OnClickListener addPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    };

    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (mCurrentUser != null) {
                mCurrentUserName = mCurrentUser.getEmail();
                Log.d("username: ", "" + mCurrentUserName);
            } else {
                Toast.makeText(NewPetActivity.this, "Usuario Sin No ha iniciado sesión", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            btn_image.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn_image.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            //this is the global variable of the photo
            photo = (Bitmap) data.getExtras().get("data");

            file = new File(getBaseContext().getExternalFilesDir(null) + "/images");
            if (!file.isDirectory()) {
                file.mkdir();
            }

            file = new File(getBaseContext().getExternalFilesDir(null) + "/images", "img_" + System.currentTimeMillis() + ".jpg");
            final_path = file.getPath();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                imageShow.setImageBitmap(photo);
                imageShow.setVisibility(View.VISIBLE);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public View.OnClickListener backToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            if (newAddress.getText().toString().isEmpty() || newComment.getText().toString().isEmpty() || final_path.isEmpty()) {
                Toast.makeText(NewPetActivity.this, "Ingrese Campos", Toast.LENGTH_LONG).show();
            } else {
                userPetObject = new UserPetObject(mCurrentUserName, newAddress.getText().toString(), final_path, newComment.getText().toString());

                byte[] imageByte = Utils.processImagePet(photo);
                saveImageInFireBase(userPetObject, imageByte);

                //TODO FIX THIS STRUCTURE TO PUT IT INTO THE RESULT OF THE TASK
                switch (callback) {
                    case 1:
                        setResult(Activity.RESULT_OK, intent);
                        FireBaseHandler.getInstance(NewPetActivity.this).savePetObject(userPetObject);
                        finish();
                        break;
                    case 2:
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        break;
                    default:
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        break;
                }

            }
        }
    };


    public void saveImageInFireBase(UserPetObject userPetObject, byte[] bitMap) {

        String uniqueImageId = UUID.randomUUID().toString();
        userPetObject.setuId(uniqueImageId);

        StorageReference storageRef = FireBaseHandler.getInstance(NewPetActivity.this).imageReferenceInFireBase(userPetObject);
        UploadTask uploadTask = storageRef.putBytes(bitMap);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewPetActivity.this, "No se guardo satisfactoriamente la imagen", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(NewPetActivity.this, "Se guardo satisfactoriamente la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
