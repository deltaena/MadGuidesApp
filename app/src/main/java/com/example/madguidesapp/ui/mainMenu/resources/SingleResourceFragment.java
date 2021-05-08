package com.example.madguidesapp.ui.mainMenu.resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
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
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.R;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;

public class SingleResourceFragment extends Fragment {

    private static final String TAG = "SingleResourceFragment";

    private Resource resource;
    private DrawerActivityViewModel drawerActivityViewModel;

    private View.OnClickListener requireAccount = click -> {
        AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
        accountRequiredDialog.show(getParentFragmentManager(), "Account Required");
    };

    private Button toggleVisitedBtn;
    private boolean isVisited = false;

    public SingleResourceFragment(RecyclerViewElement resource){
        this.resource = (Resource) resource;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_resource, container, false);

        ImageView previewImageView = view.findViewById(R.id.previewImageView);
        Glide.with(getContext())
                .load(resource.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(previewImageView);

        //TextView historicalDescriptionTextView = view.findViewById(R.id.historicalDescriptionTextView);
        //historicalDescriptionTextView.setText(resource.getHistoricalDescription());

        //TextView formalDescriptionTextView = view.findViewById(R.id.formalDescriptionTextView);
        //formalDescriptionTextView.setText(resource.getFormalDescription());

        //TextView curiositiesTextView = view.findViewById(R.id.curiositiesTextView);
        //curiositiesTextView.setText(resource.getCuriosities());

        Button showResourceOnMapsBtn = view.findViewById(R.id.showResourceOnMapsBtn);
        showResourceOnMapsBtn.setOnClickListener(click -> {
            Intent showOnMapsIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(resource.getMapsUrl()));

            startActivity(showOnMapsIntent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    if(user != null) {
                        isVisited = user.getVisitedResources().contains(resource.getName());
                    }

                    setVisitedButton();
                });

        drawerActivityViewModel.getFavoritesLiveData().
                observe(this, favorites -> {
                    if(drawerActivityViewModel.areUserRegistered()) {
                        //isFavorite = favorites.contains(resource);
                    }

                    setFavoriteButton();
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        drawerActivityViewModel.getFavoritesLiveData().removeObservers(this);
        drawerActivityViewModel.getUserLiveData().removeObservers(this);
    }

    public void setVisitedButton(){
        if(!drawerActivityViewModel.areUserRegistered()) {
            toggleVisitedBtn.setOnClickListener(requireAccount);
            toggleVisitedBtn.setText("Mark as visited");
            return;
        }

        toggleVisitedBtn.setOnClickListener(click -> {
            toggleVisitedBtn.setOnClickListener(null);
            drawerActivityViewModel.toggleResourceVisited(resource.getName());
        });

        if(isVisited){
            toggleVisitedBtn.setText("Remove from visited");
        }
        else{
            toggleVisitedBtn.setText("Mark as visited");
        }
    }

    public void setFavoriteButton(){
        /*if(!drawerActivityViewModel.areUserRegistered()) {
            markAsFavorite.setOnClickListener(requireAccount);
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
            return;
        }

        markAsFavorite.setOnClickListener(click -> {
            markAsFavorite.setOnClickListener(null);
            drawerActivityViewModel.toggleFavorite(resource);
        });

        if (isFavorite){
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.filled_heart));
        }
        else {
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
        }*/
    }
}













