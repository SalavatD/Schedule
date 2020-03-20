package com.anytrash.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schedule {
    @SerializedName("Times")
    public List<Time> times;
    @SerializedName("Data")
    public List<Datum> data;
    @SerializedName("Semestr")
    public String semestr;
}
