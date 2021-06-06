package com.example.madguidesapp.ui.mainMenu.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.R;

public class InformationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        view.findViewById(R.id.buyMeACoffeeImageView)
                .setOnClickListener(click -> {
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.buymeacoffee.com/madGuides"));

                    startActivity(intent);
                });

        return view;
    }
}