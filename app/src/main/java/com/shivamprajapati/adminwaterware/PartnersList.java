package com.shivamprajapati.adminwaterware;

public class PartnersList {

    private String name,phoneNumber,address,shopId;
    private boolean enabled;

    String getShopId() {
        return shopId;
    }

    void setShopId(String shopId) {
        this.shopId = shopId;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
