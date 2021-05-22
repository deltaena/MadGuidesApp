package com.example.madguidesapp.ui.abstracts;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;
import com.google.android.material.snackbar.Snackbar;

public abstract class FavoriteableFragment extends Fragment {

    private static final String TAG = "FavoriteableFragment";
    
    protected DrawerActivityViewModel drawerActivityViewModel;

    private TextView toolbarTitleTextView;

    private IconButton markAsFavorite;
    private boolean isFavorite = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        Toolbar toolbar = ((AppCompatActivity)requireActivity()).findViewById(R.id.toolbar);

        toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);

        markAsFavorite = toolbar.findViewById(R.id.markAsFavoriteImageButton);
        markAsFavorite.setVisibility(View.VISIBLE);

        markAsFavorite.addListener(click ->{
            if(!drawerActivityViewModel.areUserRegistered()){
                requireAccount();
                markAsFavorite.enable();
                return;
            }

            drawerActivityViewModel.toggleFavorite(getRecyclerViewElement());
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbarTitleTextView.setText(getRecyclerViewElement().getName());

        markAsFavorite.setVisibility(View.VISIBLE);

        drawerActivityViewModel.getFavoritesLiveData().
                observe(this, favorites -> {
                    if(drawerActivityViewModel.areUserRegistered()) {
                        isFavorite = favorites.contains(getRecyclerViewElement());
                    }

                    setFavoriteButton();
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        drawerActivityViewModel.getFavoritesLiveData().removeObservers(this);

        Log.d(TAG, "onPause: ");
    }

    public void setFavoriteButton(){
        if (isFavorite){
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.filled_heart));
        }
        else {
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
        }

        markAsFavorite.enable();
    }

    public void requireAccount(){
        AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
        accountRequiredDialog.show(getParentFragmentManager(), "Account Required");
    }

    protected abstract RecyclerViewElement getRecyclerViewElement();
}



















