package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.ReferenceAdapter;
import com.example.madguidesapp.android.viewPager.BaseViewPagerAdapter;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.ReferenceElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;

import java.util.ArrayList;
import java.util.List;

public class RouteReferencesFragment extends RecyclerViewBaseFragment {
    private static final String TAG = "RouteReferencesFragment";

    private List<RecyclerViewElement> references = new ArrayList<>();
    private ReferenceElement reference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reference = (ReferenceElement) getArguments().getSerializable("references");

        reference.getReferences().forEach(ref -> {
            if (reference.toString().contains("Routes/")){
                ref.get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        references.add(task.getResult().toObject(Route.class));

                        if(references.size() == reference.getReferences().size()){
                            setRecyclerViewElements(references);
                        }
                    }
                });
            }
            else if(reference.toString().contains("Resources/")){
                ref.get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        references.add(task.getResult().toObject(Resource.class));

                        if(references.size() == reference.getReferences().size()){
                            setRecyclerViewElements(references);
                        }
                    }
                });
            }
        });
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new ReferenceAdapter(requireView());
    }
}














