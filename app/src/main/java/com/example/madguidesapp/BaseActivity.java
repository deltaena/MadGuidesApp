package com.example.madguidesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    
    private NavigationView navigationView;
    private NavController navController;

    protected Toolbar toolbar;
    protected DrawerLayout drawer;

    private DrawerActivityViewModel drawerActivityViewModel;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(this).get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData()
                .observe(this, user -> {
                    this.user = user;
                    setUpHeader(user);
                });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        drawer = findViewById(R.id.drawer_layout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        initToolBar();
        initNavigationView();
        setUpHeader(null);
    }

    public void initToolBar(){
        toolbar = findViewById(R.id.toolbar);

        toolbar.findViewById(R.id.openDrawerImageButton).setOnClickListener(click -> drawer.open());

        setSupportActionBar(toolbar);
    }
    public void initNavigationView(){
        navigationView = findViewById(R.id.nav_view);
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

















