package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Marco on 4/18/2016.
 */
public class SearchSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private String[] objects;

    public SearchSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
