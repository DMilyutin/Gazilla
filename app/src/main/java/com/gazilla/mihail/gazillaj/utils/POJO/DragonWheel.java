package com.gazilla.mihail.gazillaj.utils.POJO;

public class DragonWheel {

    private int id;
    private String winType;

    public DragonWheel(int id, String winType) {
        this.id = id;
        this.winType = winType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinType() {
        return winType;
    }

    public void setWinType(String winType) {
        this.winType = winType;
    }
}
