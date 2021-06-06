package com.example.madguidesapp.ui.mainMenu.routes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Route;

public class SingleRouteFragment extends Fragment {
    private static final String TAG = "SingleRouteFragment";

    private Route route;
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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MadGuides", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("referencesOpened", false)){
            sharedPreferences.edit().putBoolean("referencesOpened", false).apply();
            Bundle bundle = new Bundle();
            bundle.putInt("selectedElementIndex", index);

            navController.popBackStack();
            navController.navigate(R.id.nav_routes_pager, bundle);
        }
    }
}













