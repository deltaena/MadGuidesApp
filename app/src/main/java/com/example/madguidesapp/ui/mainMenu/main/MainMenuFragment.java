package com.example.madguidesapp.ui.mainMenu.main;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.MainMenuAdapter;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainMenuFragment extends RecyclerViewBaseFragment {
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getMainMenuElements().
                observe(this, mainMenuElements -> {
                    setRecyclerViewElements(mainMenuElements);
                });
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new MainMenuAdapter();
    }
}










