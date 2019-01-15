package com.gazilla.mihail.gazillaj.POJO;

public class Reserve {

    private int id;
    private int userId;
    private int tableId;
    private int qty;
    private int hours;
    private String date;
    private String phone;
    private String name;
    private String commentL;

    public Reserve(int id, int userId, int tableId, int qty, int hours, String date,  String name, String phone, String commentL) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.qty = qty;
        this.hours = hours;
        this.date = date;
        this.phone = phone;
        this.name = name;
        this.commentL = commentL;
    }

    public Reserve(int qty, int hours, String date, String phone, String name, String commentL) {
        this.qty = qty;
        this.hours = hours;
        this.date = date;
        this.phone = phone;
        this.name = name;
        this.commentL = commentL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentL() {
        return commentL;
    }

    public void setCommentL(String commentL) {
        this.commentL = commentL;
    }
}
