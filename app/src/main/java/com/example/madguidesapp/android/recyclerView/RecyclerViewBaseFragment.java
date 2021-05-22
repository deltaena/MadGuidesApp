package com.example.madguidesapp.android.recyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.connectivity.ConnectivityFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.RecyclerViewElement;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class RecyclerViewBaseFragment extends ConnectivityFragment {

    private static final String TAG = "RVFragment";

    protected DrawerActivityViewModel drawerActivityViewModel;

    private List<? extends RecyclerViewElement> recyclerViewElements;
    private RecyclerView recyclerView;
    public NavController navController;
    public BaseAdapter baseAdapter;
    private ProgressBar progressBar;
    private TextView emptyRecyclerViewAdvertisementTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyRecyclerViewAdvertisementTextView = view.findViewById(R.id.emptyRecyclerViewAdvertisementTextView);
        emptyRecyclerViewAdvertisementTextView.setVisibility(GONE);
        progressBar = view.findViewById(R.id.recyclerViewProgressBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());

        setRecyclerViewElements(recyclerViewElements);
    }

    public void setRecyclerViewElements(List<? extends RecyclerViewElement> recyclerViewElements){

        if(recyclerViewElements == null){
            progressBar.setVisibility(VISIBLE);
            return;
        }

        progressBar.setVisibility(View.INVISIBLE);

        if(recyclerViewElements.isEmpty()){
            emptyRecyclerViewAdvertisementTextView.setVisibility(VISIBLE);
        }

        this.recyclerViewElements = recyclerViewElements;

        baseAdapter = getBaseAdapter();

        baseAdapter.setNavController(navController);
        baseAdapter.setRecyclerViewElements(recyclerViewElements);

        recyclerView.setAdapter(baseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    public abstract BaseAdapter getBaseAdapter();
}
