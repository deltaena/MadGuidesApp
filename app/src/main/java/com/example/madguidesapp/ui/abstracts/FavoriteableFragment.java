package com.example.madguidesapp.ui.abstracts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;
import com.google.android.material.snackbar.Snackbar;

public abstract class FavoriteableFragment extends Fragment {

    protected ConnectivityManager connectivityManager;

    protected DrawerActivityViewModel drawerActivityViewModel;

    public View.OnClickListener requireAccount = click -> {
        AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
        accountRequiredDialog.show(getParentFragmentManager(), "Account Required");
    };

    private TextView toolbarTitleTextView;

    private ImageButton markAsFavorite;
    private boolean isFavorite = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        Toolbar toolbar = ((AppCompatActivity)requireActivity()).findViewById(R.id.toolbar);

        toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);

        markAsFavorite = toolbar.findViewById(R.id.markAsFavoriteImageButton);
        markAsFavorite.setVisibility(View.VISIBLE);

        markAsFavorite.setOnClickListener(requireAccount);
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
    }

    public void setFavoriteButton(){
        if(!drawerActivityViewModel.areUserRegistered()) {
            markAsFavorite.setOnClickListener(requireAccount);
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
            return;
        }

        markAsFavorite.setOnClickListener(click -> {
            markAsFavorite.setOnClickListener(null);
            drawerActivityViewModel.toggleFavorite(getRecyclerViewElement());

            if(connectivityManager.getActiveNetwork() == null)
                Snackbar.make(getView(), "La operación se relizará cuando haya conexión", Snackbar.LENGTH_LONG).show();
        });

        if (isFavorite){
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.filled_heart));
        }
        else {
            markAsFavorite.setImageDrawable(getContext().getDrawable(R.drawable.empty_heart));
        }
    }

    protected abstract RecyclerViewElement getRecyclerViewElement();
}



















