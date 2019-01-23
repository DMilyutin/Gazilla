package com.gazilla.mihail.gazillaj.utils.POJO;

public class User {

    private int id;
    private String name;
    private String phone;
    private String email;
    private int sum ;
    private int score ;
    private int level ;
    private String refererLink;
    private int[] favorites;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getSum() {
        return sum;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public String getRefererLink() {
        return refererLink;
    }

    public int[] getFavorites() {
        return favorites;
    }
}
