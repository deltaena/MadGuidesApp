package com.example.madguidesapp.ui.mainMenu.routes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.ReferenceAdapter;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.ReferenceElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;

import java.util.ArrayList;
import java.util.List;

public class ResourcesReferencesFragment extends RecyclerViewBaseFragment {
    private static final String TAG = "RouteReferencesFragment";

    private List<RecyclerViewElement> references = new ArrayList<>();
    private ReferenceElement reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MadGuides", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("referencesOpened", true);
    }

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














