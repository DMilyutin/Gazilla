package com.gazilla.mihail.gazillaj.POJO;

public class PromoSmokerpass {

    private String expire;
    private String lastTake;

    public PromoSmokerpass(String expire, String lastTake) {
        this.expire = expire;
        this.lastTake = lastTake;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getLastTake() {
        return lastTake;
    }

    public void setLastTake(String lastTake) {
        this.lastTake = lastTake;
    }
}
