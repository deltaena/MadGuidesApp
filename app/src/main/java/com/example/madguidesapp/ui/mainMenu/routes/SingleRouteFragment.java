package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.RecyclerViewElement;

public class SingleRouteFragment extends Fragment {

    private Route route;

    public SingleRouteFragment(RecyclerViewElement recyclerViewElement){
        route = (Route) recyclerViewElement;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_route, container, false);

        DecorativeImage previewImageView = view.findViewById(R.id.previewImageView);
        previewImageView.loadImage(route.getImageUrl());

        TextView durationTextView = view.findViewById(R.id.durationTextView);
        durationTextView.setText(route.getDuration());

        TextView descriptionTextView = view.findViewById(R.id.routeDescriptionTextView);
        descriptionTextView.setText(route.getDescription());

        return view;
    }
}













