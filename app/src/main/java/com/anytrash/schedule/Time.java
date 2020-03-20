package com.anytrash.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Time {
    @SerializedName("Time")
    public String time;
    @SerializedName("Code")
    public int code;
    @SerializedName("TimeFrom")
    public Date timeFrom;
    @SerializedName("TimeTo")
    public Date timeTo;
}
