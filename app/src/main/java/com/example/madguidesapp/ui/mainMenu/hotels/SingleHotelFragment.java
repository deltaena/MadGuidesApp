package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.ui.abstracts.FavoriteableFragment;

public class SingleHotelFragment extends FavoriteableFragment {

    Hotel hotel;

    public SingleHotelFragment(RecyclerViewElement element) {
        hotel = (Hotel) element;
    }

    @Override
    protected RecyclerViewElement getRecyclerViewElement() {
        return hotel;
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