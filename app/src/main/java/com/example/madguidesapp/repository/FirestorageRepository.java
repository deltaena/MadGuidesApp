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
        UUID uuidGenerator = UUID.randomUUID();

        return storage.getReference().child("Suggestions/"+uuidGenerator).putFile(suggestionFileUri);
    }

}
















