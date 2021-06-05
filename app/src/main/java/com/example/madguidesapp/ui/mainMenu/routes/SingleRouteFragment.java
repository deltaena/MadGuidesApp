package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.pojos.RecyclerViewElement;

public class SingleRouteFragment extends Fragment {
    private static final String TAG = "SingleRouteFragment";

    private Route route;
    private boolean firstTime = true;
    private NavController navController;
    private int index;

    public SingleRouteFragment(RecyclerViewElement recyclerViewElement,
                               NavController navController, int index){
        route = (Route) recyclerViewElement;
        this.navController = navController;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_route, container, false);

        DecorativeImage previewImageView = view.findViewById(R.id.previewImageView);
        previewImageView.loadImage(route.getImageUrl());

        TextView durationTextView = view.findViewById(R.id.durationTextView);
        durationTextView.setText("Duración de la ruta: "+route.getDuration());

        TextView descriptionTextView = view.findViewById(R.id.routeDescriptionTextView);
        descriptionTextView.setText(route.getDescription());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!firstTime){
            Bundle bundle = new Bundle();
            bundle.putInt("selectedElementIndex", index);

            navController.popBackStack();
            navController.navigate(R.id.nav_routes_pager, bundle);
        }

        firstTime = false;
    }
}













