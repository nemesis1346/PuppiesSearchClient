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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.NewPetTypeAdapter;
import com.mywaytech.puppiessearchclient.models.ReportObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class ReportActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private EditText newAddress;
    private EditText newComment;
    private static final int CAMERA_REQUEST = 0;
    private Button btn_image;
    private Button btn_report;

    private ImageView imageShow;

    public static final String FRAGMENT_VALUE = "com.mywaytech.puppiessearchclient.extras.extra_fragment_value";

    public static final String TYPE_PET_SELECT_DEFAULT = "Seleccione Tipo";
    public static final String TYPE_PET_LOST = "LOST";
    public static final String TYPE_PET_ADOPTION = "ADOPTION";

    private Bitmap photo;
    private ReportObject mReportObject;

    private File file;
    private int callback;
    private String final_path="";

    private FirebaseUser mCurrentUser;
    private String mCurrentUserName;

    private FirebaseAuth mFirebaseAuth;

    private NewPetTypeAdapter mTypeAdapter;
    private Spinner mTypeSpinner;

    private String mSpinnerValue;



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

        mTypeSpinner = (Spinner) findViewById(R.id.spinner_type);
        ArrayList<String> mTypeList = new ArrayList<String>() {
        };
        mTypeList.add(TYPE_PET_SELECT_DEFAULT);
        mTypeList.add(TYPE_PET_LOST);
        mTypeList.add(TYPE_PET_ADOPTION);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(this);


        mFirebaseAuth = FireBaseHandler.getInstance(this).getFirebaseAuth();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        mCurrentUser = FireBaseHandler.getInstance(this).getFirebaseAuth().getCurrentUser();
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
                Toast.makeText(ReportActivity.this, "Usuario Sin No ha iniciado sesión", Toast.LENGTH_LONG).show();
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

            if (newAddress.getText().toString().isEmpty() ||
                    newComment.getText().toString().isEmpty() ||
                    final_path.isEmpty()|| mSpinnerValue.equals(TYPE_PET_SELECT_DEFAULT)) {

                    Toast.makeText(ReportActivity.this, R.string.validation_error_message, Toast.LENGTH_LONG).show();

            } else {
                mReportObject = new ReportObject(mCurrentUserName, newAddress.getText().toString(), final_path, newComment.getText().toString());

                byte[] imageByte = Utils.processImagePet(photo);
                saveImageInFireBase(mReportObject, imageByte);

                //TODO FIX THIS STRUCTURE TO PUT IT INTO THE RESULT OF THE TASK
                switch (callback) {
                    case 1:
                        setResult(Activity.RESULT_OK, intent);
                        FireBaseHandler.getInstance(ReportActivity.this).savePetObject(mReportObject);
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


    public void saveImageInFireBase(ReportObject reportObject, byte[] bitMap) {

        String uniqueImageId = UUID.randomUUID().toString();
        reportObject.setuId(uniqueImageId);

        StorageReference storageRef = FireBaseHandler.getInstance(ReportActivity.this).imageReferenceInFireBase(reportObject);
        UploadTask uploadTask = storageRef.putBytes(bitMap);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReportActivity.this, "No se guardo satisfactoriamente la imagen", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(ReportActivity.this, "Se guardo satisfactoriamente la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerValue = mTypeSpinner.getSelectedItem().toString();
        Log.d("type of report: ", "" + mSpinnerValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mSpinnerValue = mTypeSpinner.getSelectedItem().toString();
    }
}
