package com.example.madguidesapp;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

public class DraweActivity extends AppCompatActivity {

    private static final String TAG = "DraweActivity";

    private NavigationView navigationView;
    private NavController navController;
    protected DrawerLayout drawer;

    private DrawerActivityViewModel drawerActivityViewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            /*Toolbar toolbar = ((AppCompatActivity) requireActivity()).findViewById(R.id.toolbar);

            markAsFavorite = toolbar.findViewById(R.id.markAsFavoriteImageButton);
            markAsFavorite.setOnClickListener(requireAccount);

            toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);

            toggleVisitedBtn = view.findViewById(R.id.toggleResourceVisitedBtn);
            toggleVisitedBtn.setOnClickListener(requireAccount);*/

            TextView toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);
            toolbarTitleTextView.setText(destination.getLabel());

        });

        setUpHeader(null);

        drawerActivityViewModel = new ViewModelProvider(this).get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData()
                .observe(this, user -> {
                    this.user = user;
                    setUpHeader(user);
                });

        navigationView.setNavigationItemSelectedListener(item -> {

            if(user == null){
                AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
                accountRequiredDialog.show(getSupportFragmentManager(), "AccountRequired");
            }

            switch(item.getItemId()){
                case R.id.visitedResources:
                    drawerActivityViewModel.filterResources();
                    navController.navigate(R.id.nav_resources);
                    break;

                case R.id.favorites:
                    navController.navigate(R.id.nav_favorites);
                    break;
            }

            drawer.close();
            return true;
        });
    }

    private void setUpHeader(User user){
        View headerView = navigationView.getHeaderView(0);

        ConstraintLayout userLoggedNavHeader = headerView.findViewById(R.id.userLoggedNavHeader);
        ConstraintLayout userNotLoggedNavHeader = headerView.findViewById(R.id.userNotLoggedNavHeader);

        if(user != null) {
            userNotLoggedNavHeader.setVisibility(View.GONE);
            userLoggedNavHeader.setVisibility(View.VISIBLE);

            setUserLoggedNavHeader(headerView, user);
        }
        else {
            userNotLoggedNavHeader.setVisibility(View.VISIBLE);
            userLoggedNavHeader.setVisibility(View.GONE);

            setUserNotLoggedNavHeader(headerView);
        }
    }

    public void setUserLoggedNavHeader(View headerView, @NotNull User user){
        TextView usernameTextView = headerView.findViewById(R.id.usernameTextView),
                emailTextView = headerView.findViewById(R.id.emailTextView);

        usernameTextView.setText(user.getUsername());
        emailTextView.setText(user.getEmail());

        Button signOutBtn = headerView.findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(click -> {
            drawerActivityViewModel.signOut();
        });
    }

    public void setUserNotLoggedNavHeader(View headerView){
        Button registerBtn = headerView.findViewById(R.id.openRegisterFragmentBtn);
        Button signInBtn = headerView.findViewById(R.id.openSignInFragmentBtn);

        registerBtn.setOnClickListener(click -> {
            navController.navigate(R.id.nav_register);
            drawer.close();
        });

        signInBtn.setOnClickListener(click -> {
            navController.navigate(R.id.nav_login);
            drawer.close();
        });
    }
}