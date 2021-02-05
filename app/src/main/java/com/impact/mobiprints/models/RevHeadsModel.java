package com.impact.mobiprints.models;

public class RevHeadsModel {
    int id , revenueId;
    String revenueHead;
    String revenueCode;
    String amount;
    String dept;
    String department;
    String cate;
    String category;
    String subs;
    private String emr;
    String priceType;

    public RevHeadsModel(int id, String revenueHead, String revenueCode, int revenueId, String amount, String dept, String department, String cate, String category, String subs, String emr, String priceType) {
        this.id = id;
        this.revenueHead = revenueHead;
        this.revenueCode = revenueCode;
        this.revenueId = revenueId;
        this.amount = amount;
        this.dept = dept;
        this.department = department;
        this.cate = cate;
        this.category = category;
        this.subs = subs;
        this.emr = emr;
        this.priceType = priceType;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRevenueHead() {
        return revenueHead;
    }

    public void setRevenueHead(String revenueHead) {
        this.revenueHead = revenueHead;
    }

    public String getRevenueCode() {
        return revenueCode;
    }

    public void setRevenueCode(String revenueCode) {
        this.revenueCode = revenueCode;
    }

    public int getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(int revenueId) {
        this.revenueId = revenueId;
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

    public String getEmr() {
        return emr;
    }

    public void setEmr(String emr) {
        this.emr = emr;
    }
}
