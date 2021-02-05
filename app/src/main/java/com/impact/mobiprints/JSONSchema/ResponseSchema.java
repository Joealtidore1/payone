package com.impact.mobiprints.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSchema {
    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("phone")
    @Expose
    String phone;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("organisation")
    @Expose
    String organisation;

    @SerializedName("userId")
    @Expose
    String userId;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("lastLogin")
    @Expose
    String lastLogin;

    @SerializedName("mdacode")
    @Expose
    String mdacode;

    @SerializedName("location")
    @Expose
    String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getMdacode() {
        return mdacode;
    }

    public void setMdacode(String mdacode) {
        this.mdacode = mdacode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
