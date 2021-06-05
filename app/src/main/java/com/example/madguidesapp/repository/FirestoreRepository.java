package com.example.madguidesapp.repository;

import android.location.Location;

import com.example.madguidesapp.pojos.Comment;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Suggestion;
import com.example.madguidesapp.pojos.User;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                whereEqualTo("available", true).
                get();
    }

    public Task<QuerySnapshot> getRoutes(){
        return firestore.collection("Routes").
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getGuides(){
        return firestore.collection("Guides").get();
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

    public Task<QuerySnapshot> getRestaurantCategories(){
        return firestore.collection("Restaurant categories").
                orderBy("order", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getRestaurants(String categories){
        return firestore.collection("Restaurants").
                whereEqualTo("category", categories).
                orderBy("name", Query.Direction.ASCENDING).
                get();
    }

    public Task<QuerySnapshot> getComments(String document){
        return firestore.document(document).
                collection("Comments").
                orderBy("date", Query.Direction.DESCENDING).
                get();
    }

    public Task<Void> sendBecomeAGuideSolicitude(String email, String uid){
        DocumentReference reference = firestore.collection("Users").document(uid);

        Map<String, DocumentReference> data = new HashMap<>();
        data.put("profile", reference);

        return firestore.collection("GuideSolicitudes").document(email).set(data);
    }

    public Task<Void> insertGuide(Map<String, Object> guide, String uid){
        return firestore.collection("Guides").document(uid).set(guide);
    }

    public Task<DocumentSnapshot> findGuide(String uid){
        return firestore.collection("Guides").document(uid).get();
    }

    public Task<Void> postComment(Comment comment, RecyclerViewElement element){
        return firestore.document(element.toString()).
                collection("Comments").
                document().set(comment);
    }

    public List<Task<QuerySnapshot>> getNearByResources(GeoLocation center, double radiusInM){

        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        for (GeoQueryBounds b : bounds) {
            Query q = firestore.collection("Resources")
                    .orderBy("geohash")
                    .startAt(b.startHash)
                    .endAt(b.endHash);

            tasks.add(q.get());
        }

        return tasks;
    }

}













