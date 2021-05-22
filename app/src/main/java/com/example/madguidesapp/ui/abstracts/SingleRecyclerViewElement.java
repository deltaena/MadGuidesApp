package com.example.madguidesapp.ui.abstracts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.IconButton;

public abstract class SingleRecyclerViewElement extends FavoriteableFragment{

    private IconButton toggleVisitedImgBtn;
    private boolean isVisited = false;

    private View.OnClickListener onToggleVisitedClick = click -> {
        if(!drawerActivityViewModel.areUserRegistered()){
            requireAccount();
            toggleVisitedImgBtn.enable();
            return;
        }

        drawerActivityViewModel.toggleVisited(getRecyclerViewElement());
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IconButton showResourceOnMapsImgBtn = view.findViewById(R.id.showResourceOnMapsImgBtn);
        showResourceOnMapsImgBtn.addListener(click -> {
            Intent showOnMapsIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getMapsUrl()));

            startActivity(showOnMapsIntent);
            showResourceOnMapsImgBtn.enable();
        });

        toggleVisitedImgBtn = view.findViewById(R.id.toggleResourceVisitedImgBtn);
        toggleVisitedImgBtn.addListener(onToggleVisitedClick);

    }

    @Override
    public void onResume() {
        super.onResume();

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    if(user != null) {
                        isVisited = drawerActivityViewModel.isVisited(getRecyclerViewElement());
                    }

                    toggleVisitedImgBtn.enable();
                    setToggleVisitedImgBtn();
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        drawerActivityViewModel.getUserLiveData().removeObservers(this);
    }

    private void setToggleVisitedImgBtn(){
        if(isVisited){
            toggleVisitedImgBtn.setImageDrawable(getContext().getDrawable(R.drawable.unvisit_resource_icon));
        }
        else{
            toggleVisitedImgBtn.setImageDrawable(getContext().getDrawable(R.drawable.eye_icon));
        }
    }

    protected abstract String getMapsUrl();
}
