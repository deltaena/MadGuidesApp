package com.example.madguidesapp.pojos;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public interface ReferenceElement {
    List<DocumentReference> getReferences();

    boolean isAvailable();
}
