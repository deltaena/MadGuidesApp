package com.example.madguidesapp.android.connectivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectivityFragment extends Fragment {

    private ConnectivityManager connectivityManager;
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){
        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);

            Snackbar snackbar = Snackbar.make(getView(), "Conexión perdida", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("aceptar", v -> snackbar.dismiss());
            snackbar.show();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        try{
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }catch (IllegalArgumentException e){};
    }
}
