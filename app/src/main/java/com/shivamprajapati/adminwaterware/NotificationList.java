package com.shivamprajapati.adminwaterware;

public class NotificationList {
    private String title,body,phone,address,date;
    private boolean expandable,expanded;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    boolean isExpanded() {
        return expanded;
    }

    void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getBody() {
        return body;
    }

    void setBody(String body) {
        this.body = body;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    boolean isExpandable() {
        return expandable;
    }

    void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
