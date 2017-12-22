package com.selfcaremonitor.data_model;

/**
 * Created by User on 08/10/2017.
 */
public class VisionItem {
    String report_id;
    String vision_decimal;
    String vision_myopia_status;
    String vision_myopia_report;
    String vision_report;
    String report_date;

    public VisionItem(String report_id, String vision_decimal, String vision_myopia_status, String vision_myopia_report, String vision_report, String report_date) {
        this.report_id = report_id;
        this.vision_decimal = vision_decimal;
        this.vision_myopia_status = vision_myopia_status;
        this.vision_myopia_report = vision_myopia_report;
        this.vision_report = vision_report;
        this.report_date = report_date;
    }

    public String getReport_id() {
        return report_id;
    }

    public String getVision_decimal() {
        return vision_decimal;
    }

    public String getVision_myopia_status() {
        return vision_myopia_status;
    }

    public String getVision_myopia_report() {
        return vision_myopia_report;
    }

    public String getVision_report() {
        return vision_report;
    }

    public String getReport_date() {
        return report_date;
    }
}
