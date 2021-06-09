package com.example.madguidesapp.ui.mainMenu.map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.MapsActivityViewModel;
import com.example.madguidesapp.pojos.Resource;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
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

    private float radiusInM = 2000;
    private LatLng lastCenter;
    private Location lastLocation;

    private GoogleMap mMap;

    @SuppressLint("MissingPermission")
    private OnMapReadyCallback onMapReady = googleMap -> {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        FusedLocationProviderClient fused = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        Task<Location> task = fused.getLastLocation();

        task.addOnCompleteListener(task1 -> {
            if(task1.isSuccessful()){
                lastLocation = task1.getResult();

                showUserLocationOnMap();
                setCameraFirstTime(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
            }
        });

        mMap.setOnCameraIdleListener(() -> {
            radiusInM = updateRadius();

            float displacement = calcDisplacement();

            if(displacement > 50) {
                GeoLocation geoLocation = new GeoLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                mapsActivityViewModel.loadNearByResources(geoLocation, radiusInM);
            }
        });
    };

    private float calcDisplacement() {
        if(lastCenter == null) {
            lastCenter = mMap.getCameraPosition().target;
            return 0;
        }

        Location lastCenterLoc = new Location("lastcenter");
        lastCenterLoc.setLatitude(lastCenter.latitude);
        lastCenterLoc.setLongitude(lastCenter.longitude);

        Location centerLoc = new Location("center");
        centerLoc.setLatitude(mMap.getCameraPosition().target.latitude);
        centerLoc.setLongitude(mMap.getCameraPosition().target.longitude);

        lastCenter = mMap.getCameraPosition().target;

        return lastCenterLoc.distanceTo(centerLoc);
    }

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

                    mMap.clear();
                    showUserLocationOnMap();

                    if(resources.size() == 0) return;

                    LatLngBounds.Builder bounds = LatLngBounds.builder();

                    for(int i=0; i<resources.size(); i++){
                        Resource resource = resources.get(i);

                        mMap.addMarker(new MarkerOptions().
                                position(resource.getLatLng()).
                                title(resource.getName()).
                                icon(getBitmapDescriptor(R.drawable.ic_point_of_interest)));
                        bounds.include(resource.getLatLng());
                    }
                });

        Dexter.withContext(this).
                withPermissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).
                withListener(permissionListener).
                check();
    }

    private float updateRadius(){
        LatLng center = mMap.getCameraPosition().target;
        LatLng farRight = mMap.getProjection().getVisibleRegion().farRight;

        Location centerLoc = new Location("a");
        centerLoc.setLatitude(center.latitude);
        centerLoc.setLongitude(center.longitude);

        Location farRightLoc = new Location("b");
        farRightLoc.setLatitude(farRight.latitude);
        farRightLoc.setLongitude(farRight.longitude);

        return centerLoc.distanceTo(farRightLoc)/2;
    }

    private void showUserLocationOnMap(){
        LatLng locationLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

        mMap.addMarker(new MarkerOptions().
                position(locationLatLng).
                title("Soy yo!").
                icon(getBitmapDescriptor(R.drawable.user_icon)));
    }

    private void setCameraFirstTime(LatLng locationLatLng){
        Circle circle = mMap.addCircle(new CircleOptions().
                center(locationLatLng).radius(2000).strokeWidth(0));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, getZoomLevel(circle)));
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

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }
}
















