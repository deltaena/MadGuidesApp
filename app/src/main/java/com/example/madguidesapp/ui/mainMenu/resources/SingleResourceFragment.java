package com.example.madguidesapp.ui.mainMenu.resources;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.ui.abstracts.SingleRecyclerViewElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.R;

public class SingleResourceFragment extends SingleRecyclerViewElement {

    private static final String TAG = "SingleResourceFragment";

    private Resource resource;

    public SingleResourceFragment(RecyclerViewElement resource){
        this.resource = (Resource) resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_resource, container, false);

        DecorativeImage previewImageView = view.findViewById(R.id.previewImageView);
        previewImageView.loadImage(resource.getImageUrl());

        //TextView historicalDescriptionTextView = view.findViewById(R.id.historicalDescriptionTextView);
        //historicalDescriptionTextView.setText(resource.getHistoricalDescription());

        //TextView formalDescriptionTextView = view.findViewById(R.id.formalDescriptionTextView);
        //formalDescriptionTextView.setText(resource.getFormalDescription());

        //TextView curiositiesTextView = view.findViewById(R.id.curiositiesTextView);
        //curiositiesTextView.setText(resource.getCuriosities());

        return view;
    }

    @Override
    protected RecyclerViewElement getRecyclerViewElement() {
        return resource;
    }

    @Override
    protected String getMapsUrl() {
        return resource.getMapsUrl();
    }
}













