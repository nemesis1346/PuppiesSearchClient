package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by Marco on 4/18/2016.
 */
public class SearchRangeObject implements Serializable {
    private String range;
    private int movementValue;

    public SearchRangeObject(String range, int movementValue) {
        this.range = range;
        this.movementValue = movementValue;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getMovementValue() {
        return movementValue;
    }

    public void setMovementValue(int movementValue) {
        this.movementValue = movementValue;
    }
}
