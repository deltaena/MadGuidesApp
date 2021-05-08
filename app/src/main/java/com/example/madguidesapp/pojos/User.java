package com.example.madguidesapp.pojos;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String username;
    private List<String> visitedResources = new ArrayList<>();
    private List<String> visitedRoutes = new ArrayList<>();
    private List<DocumentReference> favorites;

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
}
