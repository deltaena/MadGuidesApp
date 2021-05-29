package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Hotel;

public class SingleHotelFragment extends Fragment {

    Hotel hotel;

    public SingleHotelFragment(RecyclerViewElement element) {
        hotel = (Hotel) element;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_hotel, container, false);

        DecorativeImage previewImageView = view.findViewById(R.id.previewImageView);
        previewImageView.loadImage(hotel.getImageUrl());

        return view;
    }
}