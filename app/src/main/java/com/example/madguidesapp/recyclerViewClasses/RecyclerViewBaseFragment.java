package com.example.madguidesapp.recyclerViewClasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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

        setToolbar();

        baseAdapter = getBaseAdapter();

        baseAdapter.setNavController(navController);
        baseAdapter.setRecyclerViewElements(this.recyclerViewElements);

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

        baseAdapter.setRecyclerViewElements(this.recyclerViewElements);
        baseAdapter.notifyDataSetChanged();
    }

    public void setToolbar(){
        /*boolean isMainMenu = navController.getCurrentDestination().getId() == R.id.nav_main_menu;

        DrawerLayout drawer = ((AppCompatActivity) requireActivity()).findViewById(R.id.drawer_layout);
        Toolbar toolbar = ((AppCompatActivity) requireActivity()).findViewById(R.id.toolbar);

        TextView textView = toolbar.findViewById(R.id.toolbarTitleTextView);
        textView.setText("some text");

        ImageButton openDrawerBtn = toolbar.findViewById(R.id.openDrawerImageButton),
                markAsFavorite = toolbar.findViewById(R.id.markAsFavoriteImageButton);

        markAsFavorite.setVisibility(View.GONE);

        if(isMainMenu){
            openDrawerBtn.setImageDrawable(getContext().getDrawable(R.drawable.three_lines_menu_icon));
            openDrawerBtn.setOnClickListener(click -> drawer.open());
        }
        else{
            openDrawerBtn.setImageDrawable(getContext().getDrawable(R.drawable.back_arrow));
            openDrawerBtn.setOnClickListener(click -> navController.popBackStack());
        }*/
    }

    public abstract BaseAdapter getBaseAdapter();
}
