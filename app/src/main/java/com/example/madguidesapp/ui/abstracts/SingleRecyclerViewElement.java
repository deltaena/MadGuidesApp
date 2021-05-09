package com.example.madguidesapp.ui.abstracts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.google.android.material.snackbar.Snackbar;

public abstract class SingleRecyclerViewElement extends FavoriteableFragment{

    private Button toggleVisitedBtn;
    private boolean isVisited = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button showResourceOnMapsBtn = view.findViewById(R.id.showResourceOnMapsBtn);
        showResourceOnMapsBtn.setOnClickListener(click -> {
            Intent showOnMapsIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getMapsUrl()));

            startActivity(showOnMapsIntent);
        });

        toggleVisitedBtn = view.findViewById(R.id.toggleResourceVisitedBtn);
        toggleVisitedBtn.setOnClickListener(requireAccount);
    }

    @Override
    public void onResume() {
        super.onResume();

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    if(user != null) {
                        isVisited = drawerActivityViewModel.isVisited(getRecyclerViewElement());
                    }

                    setVisitedButton();
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        drawerActivityViewModel.getUserLiveData().removeObservers(this);
    }

    public void setVisitedButton(){
        if(!drawerActivityViewModel.areUserRegistered()) {
            toggleVisitedBtn.setOnClickListener(requireAccount);
            toggleVisitedBtn.setText("Mark as visited");
            return;
        }

        toggleVisitedBtn.setOnClickListener(click -> {
            toggleVisitedBtn.setOnClickListener(null);
            drawerActivityViewModel.toggleVisited(getRecyclerViewElement());

            if(connectivityManager.getActiveNetwork() == null)
                Snackbar.make(getView(), "La operación se relizará cuando haya conexión", Snackbar.LENGTH_LONG).show();
        });

        if(isVisited){
            toggleVisitedBtn.setText("Remove from visited");
        }
        else{
            toggleVisitedBtn.setText("Mark as visited");
        }
    }

    protected abstract String getMapsUrl();
}
