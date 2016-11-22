package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.LocationModel;
import com.mywaytech.puppiessearchclient.tasks.LocationsAsyncTask;
import com.mywaytech.puppiessearchclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 9/19/2016.
 */
public class MapPuppiesFragment extends Fragment implements
        OnMapReadyCallback,
        LocationsAsyncTask.Callbacks,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final int MAP_CAMERA_PADDING = 150;

    private Handler mHandler = new Handler();


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

    private GoogleApiClient mClient;
    private Location mCurrentLocation;

    private List<LocationModel> mLocationModels;
    private Thread mDrawMarkersThread;


    public static MapPuppiesFragment newInstance() {
        Bundle args = new Bundle();
        MapPuppiesFragment fragment = new MapPuppiesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.mapactivity_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchText = (EditText) rootView.findViewById(R.id.edit_text_search_puppy);
        searchBtn = (Button) rootView.findViewById(R.id.btn_search);

        searchBtn.setOnClickListener(searchAddress);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_puppies);
        mapFragment.getMapAsync(this);

        permissionCheck_FINE_LOCATIONS = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        mClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishedSearch(LocationModel locationModel) {
        Log.d("onFinishedSearch", "Search finished:" + String.valueOf(locationModel.getLatitude()) + ", " + String.valueOf(locationModel.getLongitude()));
        //TODO: ASK IF THIS VALIDATION IS OK
        if (locationModel.getLongitude() != -1 && locationModel.getLatitude() != -1) {
            drawMapMarkers(locationModel);
        } else {
            Toast.makeText(getActivity(), "Dirección no Encontrada", Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener searchAddress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String addressInput = searchText.getText().toString();
                LocationsAsyncTask locationsAsyncTask = new LocationsAsyncTask(getContext());
                locationsAsyncTask.setCallback(MapPuppiesFragment.this);
                locationsAsyncTask.execute(addressInput);
            } else {
                Toast.makeText(getActivity(), "No hay conexion a Internet", Toast.LENGTH_LONG).show();
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
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d("startLocationUpdates", "" + "startL");
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (mCurrentLocation != null) {
            Log.d("onLocationChanged", "Current LocationEntity: " + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude());
            //TODO CHANGE THIS IN THE FUTURE
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            mMap.animateCamera(cameraUpdate);
            mMap.moveCamera(cameraUpdate);

            float zoomLevel = 16; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
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
        mLocationModels = new ArrayList<>();
        mLocationModels.add(new LocationModel(0, "Fundación Gora", -0.153976, -78.473185, "Las toronjas E12-161 y Av. el Inca, Quito-Ecuador"));
        mLocationModels.add(new LocationModel(1, "PAE, Protección Animal Ecuador", -0.183899, -78.494362, "Antonio de Ulloa N34-85 y Rumipamba, Quito-Ecuador"));
        mLocationModels.add(new LocationModel(2, "Urbanimal-Centro de Rescate del Municipio de Quito", -0.104485, -78.418293, "Parroquia Calderón, calle de las semillas"));

        drawMapMarkers(mLocationModels);
    }

    private void drawMapMarkers(final List<LocationModel> locations) {
        if (mMap == null) return;
        mMap.clear();
//        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
//        bounds.include(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
//        moveCamera(bounds.build());
        interruptMarkerThread();
        mDrawMarkersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (final LocationModel location : locations) {
                    if (mDrawMarkersThread.isInterrupted()) break;
                    LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                    final MarkerOptions markerOptions = new MarkerOptions()
                            .position(latlng)
                            .title(location.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Marker marker = mMap.addMarker(markerOptions);
                            marker.setTag(location);
                        }
                    });
                }
            }
        });
        mDrawMarkersThread.start();
    }

    private void moveCamera(LatLngBounds bounds) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, MAP_CAMERA_PADDING);
        mMap.moveCamera(cameraUpdate);
    }

    private void interruptMarkerThread() {
        if (mDrawMarkersThread == null) return;
        mDrawMarkersThread.interrupt();
        mDrawMarkersThread = null;
    }
}
