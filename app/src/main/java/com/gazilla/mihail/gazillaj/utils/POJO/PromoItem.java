package com.gazilla.mihail.gazillaj.utils.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PromoItem{


    @PrimaryKey
    private int id;

    private String name;
    private String description;
    private String promoType;

    public PromoItem(int id, String name, String description, String promoType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.promoType = promoType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }
}
