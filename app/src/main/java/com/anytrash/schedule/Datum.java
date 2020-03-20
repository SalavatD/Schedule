package com.anytrash.schedule;

import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("Day")
    public int day;
    @SerializedName("DayNumber")
    public int dayNumber;
    @SerializedName("Time")
    public Time time;
    @SerializedName("Class")
    public Class $class;
    @SerializedName("Group")
    public Group group;
    @SerializedName("Room")
    public Room room;
}
