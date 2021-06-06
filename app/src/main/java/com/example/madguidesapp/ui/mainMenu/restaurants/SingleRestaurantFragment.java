package com.example.madguidesapp.ui.mainMenu.restaurants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madguidesapp.R;
import com.example.madguidesapp.databinding.FragmentSingleRestaurantBinding;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Restaurant;

public class SingleRestaurantFragment extends Fragment {

    private static final String TAG = "SingleRestaurantFragmen";
    
    private Restaurant restaurant;

    private FragmentSingleRestaurantBinding binding;

    public SingleRestaurantFragment(RecyclerViewElement element){
        restaurant = (Restaurant) element;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleRestaurantBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.include5.previewImageView.loadImage(restaurant.getImageUrl());

        binding.contactLayout.urlTextView.setText(restaurant.getWeb());

        binding.contactLayout.phoneTextView.setText(restaurant.getPhone()+"");

        binding.contactLayout.referenceTextView.setText(restaurant.getReference());

        binding.descriptionTextView.setText(restaurant.getDescription());
    }
}
















