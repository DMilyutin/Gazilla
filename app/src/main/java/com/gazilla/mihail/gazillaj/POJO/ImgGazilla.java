package com.gazilla.mihail.gazillaj.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ImgGazilla {

    @PrimaryKey
    private int id;

    private String type;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public ImgGazilla(int id, String type, byte[] image) {
        this.id = id;
        this.image = image;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
