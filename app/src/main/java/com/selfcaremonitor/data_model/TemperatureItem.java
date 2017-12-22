package com.selfcaremonitor.data_model;

/**
 * Created by User on 08/10/2017.
 */
public class TemperatureItem {
    String report_id;
    String temperature;
    String report_date;

    public TemperatureItem(String report_id, String temperature, String report_date) {
        this.report_id = report_id;
        this.temperature = temperature;
        this.report_date = report_date;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getReport_date() {
        return report_date;
    }
}
