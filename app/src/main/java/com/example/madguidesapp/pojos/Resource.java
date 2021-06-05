package com.example.madguidesapp.pojos;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Objects;

public class Resource implements RecyclerViewElement , ReferenceElement{

    private String name;
    private String historicalDescription;
    private String formalDescription;
    private String curiosities;
    private Double lat, lng;
    private String imageUrl;
    private String mapsUrl;
    private boolean available;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHistoricalDescription() {
        return historicalDescription;
    }
    public void setHistoricalDescription(String historicalDescription) {
        this.historicalDescription = historicalDescription;
    }

    public String getFormalDescription() {
        return formalDescription;
    }
    public void setFormalDescription(String formalDescription) {
        this.formalDescription = formalDescription;
    }

    public String getCuriosities() {
        return curiosities;
    }
    public void setCuriosities(String curiosities) {
        this.curiosities = curiosities;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMapsUrl() {
        return mapsUrl;
    }
    public void setMapsUrl(String mapsUrl) {
        this.mapsUrl = mapsUrl;
    }

    public LatLng getLatLng(){
        return new LatLng(lat, lng);
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public List<DocumentReference> getReferences() {
        return null;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name, resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("/%s/%s", "Resources", name);
    }
}















