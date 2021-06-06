package com.example.madguidesapp.pojos;

import android.util.Log;

import java.util.Map;

public class Hotel implements RecyclerViewElement {

    private static final String TAG = "Hotel";
    
    private String name;
    private String url;
    private int phone;
    private String imageUrl;
    private String mapsUrl;
    private String reference;
    private int category;
    private Map<String, Boolean> amenities;
    private Map<String, String> optionalAmenities;

    public int getOptionalAmenity(int option){
        assert optionalAmenities != null;
        Log.d(TAG, "getOptionalAmenity: "+optionalAmenities.get("option"+option));
        return OptionalAmenities.amenities.get(optionalAmenities.get("option"+option));
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Map<String, Boolean> getAmenities() {
        return amenities;
    }

    public void setAmenities(Map<String, Boolean> amenities) {
        this.amenities = amenities;
    }

    public Map<String, String> getOptionalAmenities() {
        return optionalAmenities;
    }

    public void setOptionalAmenities(Map<String, String> optionalAmenities) {
        this.optionalAmenities = optionalAmenities;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getMapsUrl() {
        return mapsUrl;
    }

    public void setMapsUrl(String mapsUrl) {
        this.mapsUrl = mapsUrl;
    }

    @Override
    public String toString() {
        return String.format("/%s/%s", "Hotels", name);
    }
}
