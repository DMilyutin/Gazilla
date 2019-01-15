package com.gazilla.mihail.gazillaj.POJO;

public class Client {

    private int id;
    private int score;
    private int sum;
    private int level;

    private String name;
    private String phone;
    private String email;

    public Client(int id, int score, int sum, int level) {
        this.id = id;
        this.score = score;
        this.sum = sum;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getSum() {
        return sum;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
