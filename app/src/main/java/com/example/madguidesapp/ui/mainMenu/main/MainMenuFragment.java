package com.example.madguidesapp.ui.mainMenu.main;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.MainMenuElement;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.MainMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFragment extends RecyclerViewBaseFragment {

    private MainMenuViewModel mainMenuViewModel;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainMenuViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        mainMenuViewModel.getMainMenuElements().
                observe(this, mainMenuElements -> {
                    setRecyclerViewElements(mainMenuElements);
                });
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new MainMenuAdapter();
    }
}










