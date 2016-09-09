package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;

import java.util.List;

/**
 * Created by Marco on 8/9/2016.
 */
public class NewPetTypeAdapter extends ArrayAdapter<String>{

    private Context mContext;
    private List<String> mTypeList;
    private int resource;


    public NewPetTypeAdapter(Context context, int resource, List<String> typeList) {
        super(context, resource);
        this.mContext = context;
        this.resource = resource;
        this.mTypeList = typeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_simple,parent, false);
        TextView text = (TextView) view.findViewById(R.id.spinner_txt);
        text.setText(mTypeList.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_simple,parent, false);
        TextView text = (TextView) view.findViewById(R.id.spinner_txt);
        text.setText(mTypeList.get(position));
        return view;
    }

}
