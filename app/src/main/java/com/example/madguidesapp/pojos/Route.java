package com.example.madguidesapp.pojos;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Objects;

public class Route implements RecyclerViewElement, ReferenceElement{
    private String name;
    private String description;
    private String duration;
    private String imageUrl;
    private String mapsUrl;
    private List<DocumentReference> waypoints;

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

    @Override
    public List<DocumentReference> getReferences() {
        return waypoints;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    public List<DocumentReference> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<DocumentReference> waypoints) {
        this.waypoints = waypoints;
    }
}














