package com.example.madguidesapp.abstractsAndInterfaces;

import com.example.madguidesapp.R;

import java.io.Serializable;

public interface RecyclerViewElement extends Serializable {

    String getName();
    String getImageUrl();

    enum Type {
        MAIN_MENU_ELEMENT, RESOURCE, ROUTE, GUIDE, HOTEL_CATEGORY, HOTEL,
        RESTAURANT, SOCIAL_NETWORK;
    }


}



















