package com.mywaytech.puppiessearchclient.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.mywaytech.puppiessearchclient.enums.Constants;
import com.mywaytech.puppiessearchclient.models.LocationModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by m.maigua on 8/1/2016.
 */
public class LocationsAsyncTask extends AsyncTask<String, Void, LocationModel> {
    private Context mContext;
    private Callbacks callback;

    public LocationsAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected LocationModel doInBackground(String... params) {

        LocationModel locationModel = null;
        String srch = params[0] + Constants.PARAM_SEARCH;

        try {

            locationModel = GeoLocationWebService.getFromLocationName(srch);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationModel;
    }


    @Override
    protected void onPostExecute(LocationModel locationModel) {
        super.onPostExecute(locationModel);
        if (callback != null) {
            callback.onFinishedSearch(locationModel);
        }
    }

    public void setCallback(Callbacks callback) {
        this.callback = callback;
    }

    public interface Callbacks {
        void onFinishedSearch(LocationModel locationModel);
    }
}
