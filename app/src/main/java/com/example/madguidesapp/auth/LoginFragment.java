package com.example.madguidesapp.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    private EditText emailEditText, passwordEditText;

    private NavController navController;

    View.OnClickListener onLoginBtnClicked = click -> {
        String emailStr = emailEditText.getText().toString().trim().toLowerCase(),
                passwordStr = passwordEditText.getText().toString().trim();

        drawerActivityViewModel.signIn(emailStr, passwordStr);
    };

    View.OnClickListener goToRegisterClicked = click -> {
        navController.popBackStack();
        navController.navigate(R.id.nav_register);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData()
                .observe(this, user -> {
                    if(user != null){
                        Snackbar.make(requireView(), "User signed in correctly", Snackbar.LENGTH_SHORT);
                        navController.popBackStack();
                    }
                    else{
                        Snackbar.make(getView(), "Failed to sign in", Snackbar.LENGTH_SHORT);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        Button loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(onLoginBtnClicked);

        TextView goToRegisterTextView = view.findViewById(R.id.goToRegisterTextView);
        goToRegisterTextView.setOnClickListener(goToRegisterClicked);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());
    }
}



















