package com.mywaytech.puppiessearchclient.controllers;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.LocationModel;
import com.mywaytech.puppiessearchclient.services.LocationsAsyncTask;

import java.util.Map;


/**
 * Created by marco on 4/17/2016.
 */
public class MapActivity extends BaseActivity implements
        OnMapReadyCallback,
        LocationsAsyncTask.Callbacks,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{
    private static final int REQUEST_CHECK_SETTINGS = 1;
    public static final int PERMISSIONS_MAP_ACTIVITY = 0;
    private GoogleMap mMap;
    private String[] permissionsArray = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private int permissionCheck_FINE_LOCATIONS;
    private LocationRequest mLocationRequest;

    private EditText searchText;
    private Button searchBtn;
    private TextView searchResult;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private Location mCurrentLocation;

    @Override
    public int getToolbarTitle() {
        return R.string.mapactivity_title;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchText = (EditText) findViewById(R.id.edit_text_search_puppy);
        searchBtn = (Button) findViewById(R.id.btn_search);

        searchBtn.setOnClickListener(searchAddress);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_puppies);
        mapFragment.getMapAsync(this);

        permissionCheck_FINE_LOCATIONS = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        mClient =new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        permissionsArray,
                        PERMISSIONS_MAP_ACTIVITY);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MAP_ACTIVITY:
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //TODO FIX THIS
                } else {

                }
                return;
        }
    }

    @Override
    public void onFinishedSearch(LocationModel locationModel) {

        Log.d("onFinishedSearch", "Search finished:" + String.valueOf(locationModel.getLatitude()) + ", " + String.valueOf(locationModel.getLongitude()));
        //TODO: ASK IF THIS VALIDATION IS OK
        if (locationModel.getLongitude() != -1 && locationModel.getLatitude() != -1) {
            drawMapMarkers(locationModel);
        } else {
            Toast.makeText(MapActivity.this, "Dirección no Encontrada", Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener searchAddress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String addressInput = searchText.getText().toString();
                LocationsAsyncTask locationsAsyncTask = new LocationsAsyncTask(MapActivity.this);
                locationsAsyncTask.setCallback(MapActivity.this);
                locationsAsyncTask.execute(addressInput);
            } else {
                Toast.makeText(MapActivity.this, "No hay conexion a Internet", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void drawMapMarkers(LocationModel coordinates) {
        LatLng latlng = new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latlng).title("prueba"));
    }

    @Override
    public void onStart() {
        mClient.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        mClient.disconnect();
        super.onStop();

    }

    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setSmallestDisplacement(100);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        if (mCurrentLocation == null) {
                            startLocationUpdates();
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d("startLocationUpdates",""+"startL");
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (mCurrentLocation != null) {
            Log.d("onLocationChanged","Current LocationEntity: " + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
            //TODO CHANGE THIS IN THE FUTURE
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            mMap.animateCamera(cameraUpdate);
            mMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


//    public LocationModel parseToLocationModel(String locationData) {
//        LocationModel locationModelResult = new LocationModel();
//        String[] resultLocationData = locationData.split("\\s+");
//        locationModelResult.setLongitude(Long.valueOf(resultLocationData[0]));
//        locationModelResult.setLatitude(Long.valueOf(resultLocationData[1]));
//        return locationModelResult;
//    }
}
