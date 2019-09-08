package com.shivamprajapati.adminwaterware;

public class CurrentRequestDetails {

    String name;
    private String mobileNumber;
    String address;
    private String fromTime;
    private String toTime;
    String id;
    private String date;
    String[] days;
    private String status;
    private String assigned;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String getAssigned() {
        return assigned;
    }

    void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    private boolean expanded;
    private boolean viewed;


    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    String[] getDays() {
        return days;
    }

    void setDays(String[] days) {
        this.days = days;
    }

    String getFromTime() {
        return fromTime;
    }

    void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    String getToTime() {
        return toTime;
    }

    void setToTime(String toTime) {
        this.toTime = toTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getMobileNumber() {
        return mobileNumber;
    }

    void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
