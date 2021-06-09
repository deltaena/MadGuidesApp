package com.example.madguidesapp.ui.mainMenu.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.databinding.FragmentSingleResourceBinding;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Resource;

public class SingleResourceFragment extends Fragment {

    private static final String TAG = "SingleResourceFragment";

    private FragmentSingleResourceBinding binding;

    private Resource resource;
    private NavController navController;
    private int index;

    public SingleResourceFragment(RecyclerViewElement resource,
                                  NavController navController, int index){
        this.resource = (Resource) resource;
        this.navController = navController;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleResourceBinding.inflate(inflater);

        binding.include4.previewImageView.loadImage(resource.getImageUrl());

        binding.historicalDescriptionTextView.setText(
                resource.getHistoricalDescription().replace("_br", "\n\n"));

        binding.formalDescriptionTextView.setText(
                resource.getFormalDescription().replace("_br", "\n\n"));

        binding.curiositiesTextView.setText(
                resource.getCuriosities().replace("_br", "\n\n"));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.include4.previewImageView.loadImage(resource.getImageUrl());
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













