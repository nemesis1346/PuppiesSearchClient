package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.SearchRangeObject;

import java.util.List;

/**
 * Created by Marco on 4/18/2016.
 */
public class SearchSpinnerAdapter extends ArrayAdapter<SearchRangeObject> {

    private Context context;
    private List<SearchRangeObject> objects;
    private int resource;

    public SearchSpinnerAdapter(Context context, int resource, List<SearchRangeObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View transpositorItemViewDefault= LayoutInflater.from(context).inflate(R.layout.spinner_search,parent, false);
        TextView arrayTranspositorItemViewDefault= (TextView) transpositorItemViewDefault.findViewById(R.id.spinner_search);
        arrayTranspositorItemViewDefault.setText(objects.get(position).getRange());
        return transpositorItemViewDefault;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View transpositorItemView=LayoutInflater.from(context).inflate(R.layout.spinner_search,parent,false);
        TextView arrayTranspositorItemView= (TextView) transpositorItemView.findViewById(R.id.spinner_search);
        arrayTranspositorItemView.setText(objects.get(position).getRange());
        return transpositorItemView;
    }

    public int getMovementValue(int position){
        return objects.get(position).getMovementValue();
    }
}
