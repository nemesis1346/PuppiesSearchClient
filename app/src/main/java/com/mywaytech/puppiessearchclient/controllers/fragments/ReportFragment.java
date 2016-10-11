package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.models.ReportObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;
import com.mywaytech.puppiessearchclient.utils.PhotoUtils;

import java.io.File;
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
    public static final String TYPE_PET_NEW = "NEWS";

    private Bitmap mPhoto;
    private Bitmap mTemporalPhoto;
    private ReportObject mReportObject;

    private File mFile;
    private int callback;
    private String mFinalPath = "";

    private FirebaseUser mCurrentUser;
    private String mCurrentUserName;
    private NewUserObject mNewUserObject;

    private FirebaseAuth mFirebaseAuth;

    private Spinner mTypeSpinner;

    private String mSpinnerValue;

    private ProgressDialogFragment mProgressfragment;

    private StorageReference mStorageRef;
    private String mImageFirebasepPath;

    private boolean mImageFlag = false;
    private boolean mReportFlag = false;

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


        mFirebaseAuth = FireBaseHandler.getInstance(getActivity()).getFirebaseAuth();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        //TODO IT MIGHT BE UNNECESARY
        mCurrentUser = FireBaseHandler.getInstance(getActivity()).getFirebaseAuth().getCurrentUser();

        mNewUserObject = UserSessionManager.getInstance(getContext()).getLocalUser();

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

                Toast.makeText(getActivity(), "Usuario Sin No ha iniciado sesión", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            btn_image.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btn_image.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            //FIXME this is the global variable of the photo LOCAL
            mTemporalPhoto = (Bitmap) data.getExtras().get("data");

            if (mTemporalPhoto != null) {
                imageShow.setImageBitmap(mTemporalPhoto);
                imageShow.setVisibility(View.VISIBLE);
            }
        }

    }

    public View.OnClickListener backToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (newAddress.getText().toString().isEmpty() ||
                    newComment.getText().toString().isEmpty() ||
                    mTemporalPhoto == null || mSpinnerValue.equals(TYPE_PET_SELECT_DEFAULT)) {

                new AlertDialogUtils.Builder(getContext())
                        .setResourceMessage(R.string.validation_error_message)
                        .setPositiveText(R.string.btn_ok)
                        .show();

            } else {
                String uniqueId = UUID.randomUUID().toString();

                mStorageRef = FireBaseHandler.getInstance(getContext()).setImageFirebaseStorageReference(uniqueId);
                mImageFirebasepPath = "images/petImage" + uniqueId + ".jpg";

                mReportObject = new ReportObject(
                        uniqueId,
                        mNewUserObject.getmName(),
                        newAddress.getText().toString(),
                        mImageFirebasepPath,
                        newComment.getText().toString(),
                        mNewUserObject.getmEmail()

                );


                byte[] imageByte = PhotoUtils.processImagePet(mTemporalPhoto);
                saveImageInFireBase(imageByte);

                showProgress();
                mReportFlag = FireBaseHandler.getInstance(getActivity()).savePetObject(mReportObject, mSpinnerValue);
            }
        }
    };

    public void saveImageInFireBase(byte[] bitMap) {

        UploadTask uploadTask = mStorageRef.putBytes(bitMap);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mImageFlag = false;
                validateReport();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mImageFlag = true;
                validateReport();
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

    private void showProgress() {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.row_progress, null);
        TextView message = (TextView) customView.findViewById(R.id.text_progress_info);
        message.setText(R.string.report_progress);
        mProgressfragment = new ProgressDialogUtils.Builder(getContext())
                .setCustomView(customView)
                .show();
    }

    private void hideProgress() {
        if (mProgressfragment != null && mProgressfragment.isVisible()) {
            mProgressfragment.dismiss();
        }
    }

    private void validateReport() {
        if (mImageFlag && mReportFlag) {
            hideProgress();
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.success_report_saved)
                    .setIsCancelable(false)
                    .setPositiveText(R.string.btn_ok)
                    .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    })
                    .show();
        } else {
            hideProgress();
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.failure_report_saved)
                    .setIsCancelable(false)
                    .setPositiveText(R.string.btn_ok)
                    .show();
        }
    }
}


