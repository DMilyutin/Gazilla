package com.gazilla.mihail.gazillaj.utils.POJO;

import android.os.Parcel;
import android.os.Parcelable;


public class MenuItem implements Parcelable   {


    private int id;
    private String name;
    private int price;
    private String description;
    private String weight;


    public MenuItem(int id, String name, int price,String weight, String description ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
    }

    public MenuItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        description = in.readString();
        weight = in.readString();

    }


    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }


    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(description);
        dest.writeString(weight);
    }
}
