package com.example.madguidesapp.pojos;

import com.example.madguidesapp.R;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

public class HotelCategory implements RecyclerViewElement {
    private String name;
    private String imageUrl;
    private int category;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
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

}
