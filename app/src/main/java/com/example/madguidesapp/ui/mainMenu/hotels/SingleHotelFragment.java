package com.example.madguidesapp.ui.mainMenu.hotels;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.R;
import com.example.madguidesapp.databinding.FragmentSingleHotelBinding;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.RecyclerViewElement;

public class SingleHotelFragment extends Fragment {

    private static final String TAG = "SingleHotelFragment";

    private FragmentSingleHotelBinding binding;

    private Hotel hotel;

    public SingleHotelFragment(RecyclerViewElement element) {
        hotel = (Hotel) element;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSingleHotelBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.include4.previewImageView.loadImage(hotel.getImageUrl());

        binding.contactLayout.urlTextView.setText(hotel.getUrl());

        binding.contactLayout.urlTextView.setOnClickListener(click -> {
            Intent openUrlIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(hotel.getUrl()));

            startActivity(openUrlIntent);
        });

        binding.contactLayout.phoneTextView.setText(hotel.getPhone()+"");

        binding.contactLayout.phoneTextView.setOnClickListener(click -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+hotel.getPhone()));
            startActivity(callIntent);
        });

        binding.contactLayout.referenceTextView.setText(hotel.getReference());

        int wifiIcon = hotel.getAmenities().get("wifi") ? R.drawable.wifi_icon : R.drawable.no_wifi_icon;
        binding.wifiImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), wifiIcon));

        int parkingIcon = hotel.getAmenities().get("parking") ? R.drawable.parking_icon : R.drawable.no_parking_icon;
        binding.parkingImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), parkingIcon));

        int handicapIcon = hotel.getAmenities().get("handicap") ? R.drawable.handicap_icon : R.drawable.no_handicap_icon;
        binding.handicapImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), handicapIcon));

        binding.optional1ImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), hotel.getOptionalAmenity(1)));

        binding.optional2ImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), hotel.getOptionalAmenity(2)));

        binding.optional3ImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), hotel.getOptionalAmenity(3)));

    }
}


















