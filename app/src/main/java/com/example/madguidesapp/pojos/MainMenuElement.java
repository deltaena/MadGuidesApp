package com.example.madguidesapp.pojos;

import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewElement;

public class MainMenuElement implements RecyclerViewElement {

    private String name;
    private String imageUrl;
    private Type type;

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

}

















