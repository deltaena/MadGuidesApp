package com.example.madguidesapp.pojos;

import java.util.List;

public class Guide implements RecyclerViewElement {
    private String name;
    private String email;
    private String description;
    private String imageUrl;
    private List<SocialNetwork> socialNetworks;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }
    public void setSocialNetworks(List<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

}
