package com.example.madguidesapp.pojos;

import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    String text;
    Date date;
    DocumentReference user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public String getDateFormatted(){
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(date);
    }
}













