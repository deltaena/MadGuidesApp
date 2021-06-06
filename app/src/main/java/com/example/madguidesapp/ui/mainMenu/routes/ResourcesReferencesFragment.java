package com.example.madguidesapp.ui.mainMenu.routes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourcesReferencesFragment extends RecyclerViewBaseFragment {
    private static final String TAG = "RouteReferencesFragment";

    private RecyclerViewElement[] references;
    private ReferenceElement reference;

    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MadGuides", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("referencesOpened", true);

        editor.apply();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reference = (ReferenceElement) getArguments().getSerializable("references");

        index = 0;

        references = new RecyclerViewElement[reference.getReferences().size()];

        for (int i = 0; i < reference.getReferences().size(); i++) {
            DocumentReference ref = reference.getReferences().get(i);

            int finalI = i;

            if (reference.toString().contains("Routes/")) {

                ref.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        references[finalI] = task.getResult().toObject(Route.class);
                        index++;
                        if (index == reference.getReferences().size()) {
                            setRecyclerViewElements(Arrays.asList(references));
                        }
                    }
                });
            } else if (reference.toString().contains("Resources/")) {
                ref.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        references[finalI] = task.getResult().toObject(Resource.class);
                        index++;
                        if (index == reference.getReferences().size()) {
                            setRecyclerViewElements(Arrays.asList(references));
                        }
                    }
                });
            }
        }
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new ReferenceAdapter(requireView());
    }
}














