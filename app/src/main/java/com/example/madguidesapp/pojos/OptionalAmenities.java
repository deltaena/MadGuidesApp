package com.example.madguidesapp.pojos;

import com.example.madguidesapp.R;

import java.util.HashMap;
import java.util.Map;

public class OptionalAmenities {
    static Map<String, Integer> amenities = new HashMap<>();

    static{
        amenities.put("free breakfast", R.drawable.breakfast_icon);
        amenities.put("breakfast buffet", R.drawable.breakfast_icon);
        amenities.put("child-friendly", R.drawable.child_friendly_icon);
        amenities.put("air conditioning", R.drawable.air_conditioning_icon);
        amenities.put("spa", R.drawable.spa_icon);
        amenities.put("fitness centre", R.drawable.fitness_icon);
        amenities.put("pets allowed", R.drawable.pet_icon);
        amenities.put("smoke-free property", R.drawable.smoke_free_icon);
    }
}
