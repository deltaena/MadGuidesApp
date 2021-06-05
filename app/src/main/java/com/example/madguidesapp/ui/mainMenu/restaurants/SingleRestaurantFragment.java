package com.example.madguidesapp.ui.mainMenu.restaurants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madguidesapp.R;
import com.example.madguidesapp.databinding.FragmentSingleRestaurantBinding;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Restaurant;

public class SingleRestaurantFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_single_restaurant, container, false);
    }
}