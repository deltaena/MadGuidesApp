package com.example.madguidesapp.pojos;

import com.example.madguidesapp.R;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Guide implements RecyclerViewElement {
    private DocumentReference user;
    private String
            id, imageUrl, name,
            instagram = "", twitter = "", linkedin = "", other = "",
            description = "";

    public List<SocialNetwork> getSocialNetworks(){
        List<SocialNetwork> socialNetworks = new ArrayList<>();

        if(instagram != null && !instagram.isEmpty()) {
            SocialNetwork socialNetwork = new SocialNetwork();
            socialNetwork.setDrawable(R.drawable.instagram_icon);
            socialNetwork.setName("instagram");
            socialNetwork.setUrl(instagram);

            socialNetworks.add(socialNetwork);
        }

        if(twitter != null && !twitter.isEmpty()) {
            SocialNetwork socialNetwork = new SocialNetwork();
            socialNetwork.setDrawable(R.drawable.ic_twitter);
            socialNetwork.setName("twitter");
            socialNetwork.setUrl(twitter);

            socialNetworks.add(socialNetwork);
        }

        if(linkedin != null && !linkedin.isEmpty()) {
            SocialNetwork socialNetwork = new SocialNetwork();
            socialNetwork.setDrawable(R.drawable.linkedin_icon);
            socialNetwork.setName("linkedin");
            socialNetwork.setUrl(linkedin);

            socialNetworks.add(socialNetwork);
        }

        if(other != null && !other.isEmpty()) {
            SocialNetwork socialNetwork = new SocialNetwork();
            socialNetwork.setDrawable(R.drawable.link_icon);
            socialNetwork.setName("other");
            socialNetwork.setUrl(other);

            socialNetworks.add(socialNetwork);
        }

        return socialNetworks;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
