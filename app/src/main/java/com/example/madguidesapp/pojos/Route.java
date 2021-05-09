package com.example.madguidesapp.pojos;

import java.util.Objects;

public class Route implements RecyclerViewElement {
    private String name;
    private String description;
    private String duration;
    private String imageUrl;
    private String mapsUrl;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() { return duration; }
    public void setDuration(String duration) {
        this.duration = duration;
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
        Route route = (Route) o;
        return Objects.equals(name, route.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("/%s/%s", "Routes", name);
    }
}














