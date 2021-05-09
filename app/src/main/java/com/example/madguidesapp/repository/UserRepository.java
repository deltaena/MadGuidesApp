package com.example.madguidesapp.repository;

import android.util.Log;

import com.example.madguidesapp.pojos.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository {
    private static final String TAG = "UserRepository";

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    public UserRepository(){
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean areUserRegistered(){
        return firebaseAuth.getCurrentUser() != null;
    }

    public Task<QuerySnapshot> usernameExists(String username){
        return firestore.collection("Users").
                whereEqualTo("username", username).
                get();
    }

    public Task<DocumentSnapshot> getUser(){
        String email = firebaseAuth.getCurrentUser().getEmail();

        return firestore.collection("Users").
                document(email).
                get();
    }

    public Task<Void> createUser(User user){
        return firestore.collection("Users").
                document(user.getEmail()).
                set(user).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "createUser: ");
                    }
                    else{
                        firebaseAuth.getCurrentUser().delete();
                    }
                });
    }
    public Task<Void> updateUser(User user){
        return firestore.collection("Users").
                document(user.getEmail()).
                set(user);
    }

    public DocumentReference createReference(String path){
         return firestore.document(path);
    }

    public Task<Void> addReference(DocumentReference reference){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("favorites", FieldValue.arrayUnion(reference));
    }
    public Task<Void> removeReference(DocumentReference reference){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("favorites", FieldValue.arrayRemove(reference));
    }

    public Task<Void> addResource(String name){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("visitedResources", FieldValue.arrayUnion(name));

    }
    public Task<Void> removeResource(String name){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("visitedResources", FieldValue.arrayRemove(name));
    }

    public Task<Void> addRoute(String name){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("visitedRoutes", FieldValue.arrayUnion(name));

    }
    public Task<Void> removeRoute(String name){
        return firestore.collection("Users").
                document(firebaseAuth.getCurrentUser().getEmail()).
                update("visitedRoutes", FieldValue.arrayRemove(name));
    }

    public Task<AuthResult> register(User user, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password);
    }
    public Task<AuthResult> signIn(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }
    public void signOut(){
        firebaseAuth.signOut();
    }
}














