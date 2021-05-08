package com.example.madguidesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;

public abstract class FavoriteableFragment extends Fragment {

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

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);

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



















