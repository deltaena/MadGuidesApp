package com.example.madguidesapp.pojos;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String email;
    private String username;
    private String imageUrl;
    private int guideStatus;
    private String address;
    private List<String> visitedResources = new ArrayList<>();
    private List<String> visitedRoutes = new ArrayList<>();
    private List<DocumentReference> favorites = new ArrayList<>();

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGuideStatus() {
        return guideStatus;
    }

    public void setGuideStatus(int guideStatus) {
        this.guideStatus = guideStatus;
    }

    public String getAddress() {
        return (address == null) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getVisitedResources() {
        return visitedResources;
    }
    public void setVisitedResources(List<String> visitedResources) {
        this.visitedResources = visitedResources;
    }

    public List<String> getVisitedRoutes() {
        return visitedRoutes;
    }
    public void setVisitedRoutes(List<String> visitedRoutes) {
        this.visitedRoutes = visitedRoutes;
    }

    public List<DocumentReference> getFavorites(){
        return favorites;
    }
    public void setFavorites(List<DocumentReference> favorites) {
        this.favorites = favorites;
    }

    public class SolicitudeStatus{
        public static final int NOT_SOLICITED = -1,
                         PENDING = 0,
                         APPROVED = 1,
                         DENIED = -2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(imageUrl, user.imageUrl) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, address);
    }
}














