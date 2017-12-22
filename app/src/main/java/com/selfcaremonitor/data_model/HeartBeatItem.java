package com.selfcaremonitor.data_model;

/**
 * Created by User on 08/10/2017.
 */
public class HeartBeatItem {
    String report_id;
    String heart_beats;
    String state;
    String report_date;

    public HeartBeatItem(String report_id, String heart_beats, String state, String report_date) {
        this.report_id = report_id;
        this.heart_beats = heart_beats;
        this.state = state;
        this.report_date = report_date;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getHeart_beats() {
        return heart_beats;
    }

    public String getState() {
        return state;
    }

    public String getReport_date() {
        return report_date;
    }
}
