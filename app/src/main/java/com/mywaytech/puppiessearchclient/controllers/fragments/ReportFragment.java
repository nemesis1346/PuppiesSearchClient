package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Marco on 19/9/2016.
 */
public class ReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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
    private String final_path = "";

    private FirebaseUser mCurrentUser;
    private String mCurrentUserName;

    private FirebaseAuth mFirebaseAuth;

    private Spinner mTypeSpinner;

    private String mSpinnerValue;


    public static ReportFragment newInstance() {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.report_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newAddress = (EditText) rootView.findViewById(R.id.edit_text_address_lost_activity);
        newComment = (EditText) rootView.findViewById(R.id.edit_text_comment_lost_activity);

        btn_image = (Button) rootView.findViewById(R.id.input_image);
        btn_report = (Button) rootView.findViewById(R.id.btn_reportar);

        imageShow = (ImageView) rootView.findViewById(R.id.showImage);

        btn_image.setOnClickListener(addPhoto);
        btn_report.setOnClickListener(backToActivity);

        mTypeSpinner = (Spinner) rootView.findViewById(R.id.spinner_type);
        ArrayList<String> mTypeList = new ArrayList<String>() {
        };
        mTypeList.add(TYPE_PET_SELECT_DEFAULT);
        mTypeList.add(TYPE_PET_LOST);
        mTypeList.add(TYPE_PET_ADOPTION);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(this);


        mFirebaseAuth = FireBaseHandler.getInstance(getContext()).getFirebaseAuth();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        mCurrentUser = FireBaseHandler.getInstance(getContext()).getFirebaseAuth().getCurrentUser();
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                Toast.makeText(getContext(), "Usuario Sin No ha iniciado sesión", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            btn_image.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn_image.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            //this is the global variable of the photo
            photo = (Bitmap) data.getExtras().get("data");

            file = new File(getContext().getExternalFilesDir(null) + "/images");
            if (!file.isDirectory()) {
                file.mkdir();
            }

            file = new File(getContext().getExternalFilesDir(null) + "/images", "img_" + System.currentTimeMillis() + ".jpg");
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


            if (newAddress.getText().toString().isEmpty() ||
                    newComment.getText().toString().isEmpty() ||
                    final_path.isEmpty() || mSpinnerValue.equals(TYPE_PET_SELECT_DEFAULT)) {

                Toast.makeText(getActivity(),getResources().getString(R.string.validation_error_message), Toast.LENGTH_LONG).show();

            } else {
                mReportObject = new ReportObject(mCurrentUserName, newAddress.getText().toString(), final_path, newComment.getText().toString());

                byte[] imageByte = Utils.processImagePet(photo);
                saveImageInFireBase(mReportObject, imageByte);


                FireBaseHandler.getInstance(getActivity()).savePetObject(mReportObject, mSpinnerValue);


            }
        }
    };

    public void saveImageInFireBase(ReportObject reportObject, byte[] bitMap) {

        String uniqueImageId = UUID.randomUUID().toString();
        reportObject.setuId(uniqueImageId);

        StorageReference storageRef = FireBaseHandler.getInstance(getActivity()).imageReferenceInFireBase(reportObject);
        UploadTask uploadTask = storageRef.putBytes(bitMap);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), getResources().getString(R.string.success_image_saved), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(getActivity(), getResources().getString(R.string.failure_image_saved), Toast.LENGTH_SHORT).show();
                getActivity().finish();

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


