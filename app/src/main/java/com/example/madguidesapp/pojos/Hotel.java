package com.example.madguidesapp.pojos;

import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

public class Hotel implements RecyclerViewElement {

    private String name;
    private String imageUrl;
    private int category;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

}
