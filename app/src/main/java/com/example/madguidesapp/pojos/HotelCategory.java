package com.example.madguidesapp.pojos;

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
