package com.example.madguidesapp.pojos;

import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewElement;

import java.util.Objects;

public class Resource implements RecyclerViewElement {

    private String name;
    private String historicalDescription;
    private String formalDescription;
    private String curiosities;
    private String imageUrl;
    private String mapsUrl;

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















