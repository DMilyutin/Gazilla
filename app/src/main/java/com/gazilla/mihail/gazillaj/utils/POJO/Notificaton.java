package com.gazilla.mihail.gazillaj.utils.POJO;

public class Notificaton {

    private int id;
    private String message;
    private int[] commands;
    private int promoId;

    public Notificaton(int id, String message, int[] commands, int promoId) {
        this.id = id;
        this.message = message;
        this.commands = commands;
        this.promoId = promoId;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int[] getCommands() {
        return commands;
    }

    public int getPromoId() {
        return promoId;
    }
}
