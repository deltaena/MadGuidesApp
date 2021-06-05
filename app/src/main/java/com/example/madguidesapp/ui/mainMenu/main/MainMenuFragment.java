package com.example.madguidesapp.ui.mainMenu.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.MainMenuAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.security.Provider;

import static android.content.Context.LOCATION_SERVICE;

public class MainMenuFragment extends RecyclerViewBaseFragment {
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getMainMenuElements().
                observe(this, mainMenuElements ->
                    setRecyclerViewElements(mainMenuElements));
    }

    @Override
    public void onStart() {
        super.onStart();

        drawerActivityViewModel.setRoutesFilter(false);
        drawerActivityViewModel.setResourcesFilter(false);
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new MainMenuAdapter(click -> {
            Dexter.withContext(requireContext()).
                    withPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                    withListener(new PermissionListener() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                            String bestProvider = locationManager.getBestProvider(new Criteria(), false);

                            Location location = locationManager.getLastKnownLocation(bestProvider);

                            location.getLatitude();
                            location.getLongitude();



                            drawerActivityViewModel.getNearbyResources();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    });
        });
    }
}










