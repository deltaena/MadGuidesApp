package com.example.madguidesapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class AppRepository {

    private static final String TAG = "AppRepository";

    private final FirebaseFirestore firestore;

    public AppRepository(){
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<QuerySnapshot> getMainMenuElements(){
        return firestore.collection("Main Menu Elements").
                orderBy("order", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getResources() {
        return firestore.collection("Resources").
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getRoutes(){
        return firestore.collection("Routes").
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getGuides(){
        return firestore.collection("Guides").
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getHotelCategories(){
        return firestore.collection("Hotel Categories").
                orderBy("category", Query.Direction.DESCENDING).
                get();
    }

    public Task<QuerySnapshot> getHotels(int categoryFilter){
        return firestore.collection("Hotels").
                whereEqualTo("category", categoryFilter).
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }
}













