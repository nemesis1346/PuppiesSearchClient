package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by Marco on 4/18/2016.
 */
public class SearchRangeObject implements Serializable {
    private String range;
    private int value;

    public SearchRangeObject(String range, int value) {
        this.range = range;
        this.value = value;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
