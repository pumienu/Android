package com.example.test_giua_ki.Dress.Model;


import android.net.Uri;

public class Dress {
    private int id;
    private String name;
    private String code;
    private float price;
    private String desc;
    private Uri image;

    public Dress(int id, String name, String code, float price, String desc, Uri image) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.price = price;
        this.desc = desc;
        this.image = image;
    }

    public Dress(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
