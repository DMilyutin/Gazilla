package com.gazilla.mihail.gazillaj.POJO;

public class UserWithKeys {

    private int id;
    private String name;
    private String phone;
    private String email;
    private int sum ;
    private int score ;
    private int level ;
    private String publickey;
    private String privatekey;
    private String refererLink;
    private String promo;
    private int[] favorites;

    public UserWithKeys(int id, String name, String phone, String email, int sum, int score,
                        int level, String publickey, String privatekey, String refererLink,
                        String promo ,int[] favorites) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sum = sum;
        this.score = score;
        this.level = level;
        this.publickey = publickey;
        this.privatekey = privatekey;
        this.refererLink = refererLink;
        this.promo = promo;
        this.favorites = favorites;
    }

    public int getId() {
        return id;
    }

    public UserWithKeys(int id,String publickey, String privatekey) {
        this.id = id;
        this.publickey = publickey;
        this.privatekey = privatekey;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRefererLink(String refererLink) {
        this.refererLink = refererLink;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public String getRefererLink() {
        return refererLink;
    }

    public String getPublickey() {
        return publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public int[] getFavorites() {
        return favorites;
    }

    public void setFavorites(int[] favorites) {
        this.favorites = favorites;
    }

    public String getPromo() {
        return promo;
    }




}
