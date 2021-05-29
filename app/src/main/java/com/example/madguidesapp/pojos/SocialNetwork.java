package com.example.madguidesapp.pojos;

import android.graphics.drawable.Drawable;

public class SocialNetwork implements RecyclerViewElement {

    private String name;
    private String imageUrl;
    private int drawable;
    private String url;

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

    @Override
    public String getMapsUrl() {
        return null;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
