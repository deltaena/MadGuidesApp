package com.example.madguidesapp.android.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class IconButton extends androidx.appcompat.widget.AppCompatImageView {

    private ConnectivityManager connectivityManager;
    private List<OnClickListener> listeners = new ArrayList<>();

    public IconButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        int[] attributes = {
                R.attr.performsAsync
        };

        TypedArray typedArray = context.obtainStyledAttributes(attrs, attributes);

        boolean performsAsync = typedArray.getBoolean(0, false);

        if(performsAsync) {
            listeners.add(click -> {
                this.setBackgroundColor(Color.RED);
                this.setClickable(false);
            });
        }

        this.setClickable(true);
        this.setBackgroundColor(Color.GREEN);
        this.setOnClickListener(click -> {
            if(connectivityManager.getActiveNetwork() == null)
                Snackbar.make(click.getRootView(), "La operación se relizará cuando haya conexión", Snackbar.LENGTH_LONG).show();

            listeners.forEach(listener -> listener.onClick(click));
        });
    }

    /*
     * Single responsibility
     * Open-closed
     * Liskov substitution
     * Interface segregation
     * Dependency inversion
     */
    public void initButton(){

    }

    public void addListener(@Nullable OnClickListener l) {
        if(listeners.size() == 2){
            listeners.remove(listeners.size()-1);
        }

        listeners.add(l);
    }

    public void enable(){
        this.setBackgroundColor(Color.GREEN);
        this.setClickable(true);
    }
}

















