package com.example.madguidesapp.ui.mainMenu.routes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;

public class SingleRouteFragment extends Fragment {

    private Route route;
    private DrawerActivityViewModel drawerActivityViewModel;

    private View.OnClickListener requireAccount = click -> {
        AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
        accountRequiredDialog.show(getParentFragmentManager(), "Account Required");
    };

    private ImageButton markAsFavorite;
    private TextView toolbarTitleTextView;
    private boolean isFavorite = false;

    private Button toggleVisitedBtn;
    private boolean isVisited = false;

    public SingleRouteFragment(RecyclerViewElement recyclerViewElement){
        route = (Route) recyclerViewElement;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_route, container, false);

        Toolbar toolbar = ((AppCompatActivity) requireActivity()).findViewById(R.id.toolbar);

        markAsFavorite = toolbar.findViewById(R.id.markAsFavoriteImageButton);
        markAsFavorite.setOnClickListener(requireAccount);

        toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);

        ImageView previewImageView = view.findViewById(R.id.previewImageView);
        Glide.with(getContext())
                .load(route.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(previewImageView);

        TextView durationTextView = view.findViewById(R.id.durationTextView);
        durationTextView.setText(route.getDuration());

        TextView descriptionTextView = view.findViewById(R.id.routeDescriptionTextView);
        descriptionTextView.setText(route.getDescription());

        Button showResourceOnMapsBtn = view.findViewById(R.id.showResourceOnMapsBtn);
        showResourceOnMapsBtn.setOnClickListener(click -> {
            Intent showOnMapsIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(route.getMapsUrl()));

            startActivity(showOnMapsIntent);
        });

        toggleVisitedBtn = view.findViewById(R.id.toggleResourceVisitedBtn);
        toggleVisitedBtn.setOnClickListener(requireAccount);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbarTitleTextView.setText(route.getName());
        markAsFavorite.setVisibility(View.VISIBLE);

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    if(user != null) {
                        isVisited = user.getVisitedRoutes().contains(route.getName());
                    }

                    setVisitedButton();
                });

        drawerActivityViewModel.getFavoritesLiveData().
                observe(this, favorites -> {
                    if(drawerActivityViewModel.areUserRegistered()){
                        isFavorite = favorites.contains(route);
                    }

                    setFavoriteButton();
                });
    }

    @Override
    public void onStop() {
        super.onStop();

        drawerActivityViewModel.getFavoritesLiveData().removeObservers(this);
        drawerActivityViewModel.getUserLiveData().removeObservers(this);
    }

    public void setVisitedButton(){
        if(!drawerActivityViewModel.areUserRegistered()){
            toggleVisitedBtn.setOnClickListener(requireAccount);
            toggleVisitedBtn.setText("Mark as visited");
            return;
        }

        toggleVisitedBtn.setOnClickListener(click -> {
            toggleVisitedBtn.setOnClickListener(null);
            drawerActivityViewModel.toggleRouteVisited(route.getName());
        });

        if(isVisited){
            toggleVisitedBtn.setText("Remove from visited");
        }
        else{
            toggleVisitedBtn.setText("Mark as visited");
        }
    }

    public void setFavoriteButton(){
        if(!drawerActivityViewModel.areUserRegistered()) {
            markAsFavorite.setOnClickListener(requireAccount);
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
            return;
        }

        markAsFavorite.setOnClickListener(click -> {
            markAsFavorite.setOnClickListener(null);
            drawerActivityViewModel.toggleFavorite(route);
        });

        if (isFavorite){
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.filled_heart));
        }
        else {
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
        }
    }
}













