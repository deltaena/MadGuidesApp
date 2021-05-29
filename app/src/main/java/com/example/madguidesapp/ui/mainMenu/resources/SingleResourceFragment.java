package com.example.madguidesapp.ui.mainMenu.resources;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.databinding.FragmentSingleResourceBinding;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.RecyclerViewElement;

public class SingleResourceFragment extends Fragment {

    private static final String TAG = "SingleResourceFragment";

    private FragmentSingleResourceBinding binding;

    private Resource resource;

    public SingleResourceFragment(RecyclerViewElement resource){
        this.resource = (Resource) resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleResourceBinding.inflate(inflater);

        //DecorativeImage previewImageView = view.findViewById(R.id.previewImageView);
        //previewImageView.loadImage(resource.getImageUrl());

        //TextView historicalDescriptionTextView = view.findViewById(R.id.historicalDescriptionTextView);
        //historicalDescriptionTextView.setText(resource.getHistoricalDescription());

        //TextView formalDescriptionTextView = view.findViewById(R.id.formalDescriptionTextView);
        //formalDescriptionTextView.setText(resource.getFormalDescription());

        //TextView curiositiesTextView = view.findViewById(R.id.curiositiesTextView);
        //curiositiesTextView.setText(resource.getCuriosities());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.include4.previewImageView.loadImage(resource.getImageUrl());
    }
}













