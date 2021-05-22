package com.example.madguidesapp.repository;

import com.example.madguidesapp.pojos.Suggestion;
import com.example.madguidesapp.pojos.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirestoreRepository {

    private static final String TAG = "AppRepository";

    private final FirebaseFirestore firestore;

    public FirestoreRepository(){
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<Void> uploadSuggestion(Suggestion suggestion){
        return firestore.collection("Suggestions").document().set(suggestion);
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

    public Task<Void> sendBecomeAGuideSolicitude(User user){
        DocumentReference reference = firestore.collection("Users").document(user.getEmail());

        Map<String, DocumentReference> data = new HashMap<>();
        data.put("profile", reference);

        return firestore.collection("GuideSolicitudes").document(user.getEmail()).set(data);
    }

}













