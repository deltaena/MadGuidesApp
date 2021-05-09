package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.abstracts.SingleRecyclerViewElement;

public class SingleRouteFragment extends SingleRecyclerViewElement {

    private Route route;

    public SingleRouteFragment(RecyclerViewElement recyclerViewElement){
        route = (Route) recyclerViewElement;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_route, container, false);

        ImageView previewImageView = view.findViewById(R.id.previewImageView);
        Glide.with(getContext())
                .load(route.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(previewImageView);

        TextView durationTextView = view.findViewById(R.id.durationTextView);
        durationTextView.setText(route.getDuration());

        TextView descriptionTextView = view.findViewById(R.id.routeDescriptionTextView);
        descriptionTextView.setText(route.getDescription());

        return view;
    }

    @Override
    protected String getMapsUrl() {
        return route.getMapsUrl();
    }

    @Override
    protected RecyclerViewElement getRecyclerViewElement() {
        return route;
    }
}













