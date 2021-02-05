package com.impact.mobiprints.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RevenueHeads {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("code")
    @Expose
    String code;

    @SerializedName("amount")
    @Expose
    String amount;

    @SerializedName("dept")
    @Expose
    String dept;

    @SerializedName("department")
    @Expose
    String department;

    @SerializedName("cate")
    @Expose
    String cate;

    @SerializedName("category")
    @Expose
    String category;

    @SerializedName("subs")
    @Expose
    String subs;

    @SerializedName("name")
    @Expose
    String revenueHead;

    @SerializedName("emr")
    @Expose
    int emr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubs() {
        return subs;
    }

    public void setSubs(String subs) {
        this.subs = subs;
    }

    public String getRevenueHead() {
        return revenueHead;
    }

    public void setRevenueHead(String revenueHead) {
        this.revenueHead = revenueHead;
    }

    public int getEmr() {
        return emr;
    }

    public void setEmr(int emr){
        this.emr = emr;
    }
}
