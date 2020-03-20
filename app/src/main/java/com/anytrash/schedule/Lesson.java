package com.anytrash.schedule;

public class Lesson {
    private final String name;
    private final String time;

    public Lesson(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
