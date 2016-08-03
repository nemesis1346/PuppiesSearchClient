package com.mywaytech.puppiessearchclient.services;

import android.location.Address;
import android.util.Log;

import com.google.firebase.auth.api.model.StringList;
import com.mywaytech.puppiessearchclient.enums.Constants;
import com.mywaytech.puppiessearchclient.models.LocationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.List;

/**
 * Created by m.maigua on 8/2/2016.
 */
public class GeoLocationWebService {

    public static LocationModel getFromLocationName(String LocationName) throws IOException {
        String contentAsString;
        List<Address> list = null;
        InputStream in = null;
        int len = 500;
        JSONObject jsonObjectResult;
        JSONObject location;
        double latitude;
        double longitude;
        LocationModel locationModel=new LocationModel();

        try {
            URL url = new URL(
                    (Constants.GEOCODING_SERVICE_URL +
                            Constants.GEOCODING_SERVICE_PARAM_ADDRESS +
                            Constants.URL_EQUALS +
                            LocationName +
                            Constants.URL_AMPERSAND +
                            Constants.GEOCODING_SERVICE_PARAM_SENSOR +
                            Constants.URL_EQUALS +
                            String.valueOf(false)).replace(Constants.STRING_SPACE, Constants.STRING_PLUS)
            );
            Log.d("print url: ", " " + url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constants.READ_TIMEOUT);
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(Constants.REQUEST_METHOD_GET);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            int response = urlConnection.getResponseCode();
            Log.d("RESPONSE", "The response is: " + response);
            in = urlConnection.getInputStream();


            contentAsString = processResponse(in, len);
            Log.d("RESPONSE", "The responseString is: " + contentAsString);

            jsonObjectResult = new JSONObject(contentAsString);

            location = jsonObjectResult.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");

            latitude = location.getDouble("lat");
            longitude = location.getDouble("lng");

            locationModel.setLatitude(latitude);
            locationModel.setLongitude(longitude);

            return locationModel;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                in.close();
                //urlConnection.disconnect();
            }

        }
    }

    public static String processResponse(InputStream stream, int len) throws IOException, UnsupportedEncodingException{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }
        baos.flush();
        return  new String(baos.toByteArray(), "UTF-8");

    }


}
