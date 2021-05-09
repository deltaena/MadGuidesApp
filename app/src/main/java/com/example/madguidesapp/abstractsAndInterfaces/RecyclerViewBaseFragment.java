package com.example.madguidesapp.abstractsAndInterfaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewBaseFragment extends Fragment {

    private static final String TAG = "RVFragment";

    private List<? extends RecyclerViewElement> recyclerViewElements = new ArrayList<>();
    private RecyclerView recyclerView;
    public NavController navController;
    public BaseAdapter baseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());

        baseAdapter = getBaseAdapter();

        baseAdapter.setNavController(navController);
        baseAdapter.setRecyclerViewElements(recyclerViewElements);

        recyclerView.setAdapter(baseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void onResume() {
        super.onResume();

        baseAdapter.setRecyclerViewElements(this.recyclerViewElements);
        baseAdapter.notifyDataSetChanged();
    }

    public void setRecyclerViewElements(List<? extends RecyclerViewElement> recyclerViewElements){
        if(recyclerViewElements == null){
            navController.popBackStack();
            return;
        }
        this.recyclerViewElements = recyclerViewElements;

        baseAdapter.setRecyclerViewElements(recyclerViewElements);
        baseAdapter.notifyDataSetChanged();
    }

    public abstract BaseAdapter getBaseAdapter();
}
