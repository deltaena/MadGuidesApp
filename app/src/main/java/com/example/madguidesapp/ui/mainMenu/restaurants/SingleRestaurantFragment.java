package com.example.madguidesapp.ui.mainMenu.restaurants;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        binding.contactLayout.urlTextView.setOnClickListener(click -> {
            Intent openUrlIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(restaurant.getWeb()));

            startActivity(openUrlIntent);
        });

        binding.contactLayout.phoneTextView.setText(restaurant.getPhone()+"");
        binding.contactLayout.phoneTextView.setOnClickListener(click -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+restaurant.getPhone()));
            startActivity(callIntent);
        });

        binding.contactLayout.referenceTextView.setText(restaurant.getReference());
        binding.contactLayout.referenceTextView.setOnClickListener(click -> {
            Intent openUrlIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(restaurant.getReference()));

            startActivity(openUrlIntent);
        });

        binding.descriptionTextView.setText(restaurant.getDescription().replace("_br", " \n"));
    }
}
















