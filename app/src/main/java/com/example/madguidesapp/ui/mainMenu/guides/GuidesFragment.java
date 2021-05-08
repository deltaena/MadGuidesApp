package com.example.madguidesapp.ui.mainMenu.guides;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.GuidesAdapter;

import java.util.List;

public class GuidesFragment extends RecyclerViewBaseFragment {

    private GuidesViewModel guidesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        guidesViewModel = new ViewModelProvider(this).get(GuidesViewModel.class);
        guidesViewModel.getGuidesLiveData().
                observe(this, guides -> setRecyclerViewElements(guides));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new GuidesAdapter();
    }
}
