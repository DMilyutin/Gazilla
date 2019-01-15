package com.gazilla.mihail.gazillaj.POJO;

public class Success {
    private boolean success;
    private String message;

    public Success(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
