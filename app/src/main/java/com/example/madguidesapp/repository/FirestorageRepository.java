package com.example.madguidesapp.repository;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class FirestorageRepository {

    private FirebaseStorage storage;

    public FirestorageRepository(){
        storage = FirebaseStorage.getInstance();
    }

    public UploadTask uploadSuggestionImage(Uri suggestionFileUri){
        return uploadImage("Suggestions", suggestionFileUri);
    }
    public UploadTask uploadProfileImage(Uri profileImageUri){
        return uploadImage("ProfilePhotos", profileImageUri);
    }

    private UploadTask uploadImage(String folder, Uri fileUri){
        return storage.getReference().child(folder+"/"+UUID.randomUUID()).putFile(fileUri);
    }

}
















