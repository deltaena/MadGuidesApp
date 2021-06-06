package com.example.madguidesapp.ui.mainMenu.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.MapsActivityViewModel;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity {

    private static final String TAG = "MapsActivity";

    private MapsActivityViewModel mapsActivityViewModel;
    private View root;

    private final double radiusInM = 6000;

    private GoogleMap mMap;

    @SuppressLint("MissingPermission")
    private OnMapReadyCallback onMapReady = googleMap -> {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String bestProvider = locationManager.getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location == null) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setFastestInterval(5000);

            FusedLocationProviderClient fused = LocationServices.getFusedLocationProviderClient(getApplicationContext());

            fused.requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        showUserLocationOnMap(locationResult.getLastLocation());
                    }
                }, null);
            return;
        }

        showUserLocationOnMap(location);
    };

    private MultiplePermissionsListener permissionListener = new MultiplePermissionsListener() {

        @Override
        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
            if(multiplePermissionsReport.areAllPermissionsGranted()){
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(onMapReady);
                return;
            }

            Snackbar.make(root, getString(R.string.locationDenied), Snackbar.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)) {
                permissionToken.continuePermissionRequest();
            } else {
                permissionToken.cancelPermissionRequest();
                Snackbar.make(root, getString(R.string.locationPermaDenied), Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        root = findViewById(R.id.map);

        mapsActivityViewModel = new ViewModelProvider(this).get(MapsActivityViewModel.class);
        mapsActivityViewModel.getResourcesLiveData().
                observe(this, resources -> {
                    Snackbar.make(root, "Mostrando " + resources.size() + " recursos cercanos", Snackbar.LENGTH_LONG).show();
                    resources.forEach(resource -> {
                        mMap.addMarker(new MarkerOptions().
                                position(resource.getLatLng()).
                                title(resource.getName()).
                                icon(getBitmapDescriptor(R.drawable.ic_point_of_interest)));
                    });
                });

        Dexter.withContext(this).
                withPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).
                withListener(permissionListener).
                check();
    }

    private void showUserLocationOnMap(Location location){
        LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().
                position(locationLatLng).
                title("Soy yo!").
                icon(getBitmapDescriptor(R.drawable.user_icon)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 12));

        mMap.addCircle(new CircleOptions().center(locationLatLng).radius(radiusInM).
                strokeWidth(10).strokeColor(Color.BLACK));

        GeoLocation center = new GeoLocation(location.getLatitude(), location.getLongitude());

        mapsActivityViewModel.loadNearByResources(center, radiusInM);
    }

    private BitmapDescriptor getBitmapDescriptor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

            int h = vectorDrawable.getIntrinsicHeight();
            int w = vectorDrawable.getIntrinsicWidth();

            vectorDrawable.setBounds(0, 0, w, h);

            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bm);

        } else {
            return BitmapDescriptorFactory.fromResource(id);
        }
    }
}
















