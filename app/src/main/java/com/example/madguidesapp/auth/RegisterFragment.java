package com.example.madguidesapp.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.User;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    private Button registerBtn;

    private TextView alreadyHaveAnAccountTextView;

    private EditText emailEditText, usernameEditText, passwordEditText;

    private NavController navController;

    View.OnClickListener onRegisterBtnClicked = click -> {
        String emailStr = emailEditText.getText().toString().trim().toLowerCase();
        String usernameStr = usernameEditText.getText().toString().trim();
        String passwordStr = passwordEditText.getText().toString().trim();

        User user = new User();

        user.setEmail(emailStr);
        user.setUsername(usernameStr);

        drawerActivityViewModel.register(user, passwordStr);
    };

    View.OnClickListener goToLoginClicked = click -> {
        navController.popBackStack();
        navController.navigate(R.id.nav_login);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData()
                .observe(this, user -> {
                    if(user != null){
                        Snackbar.make(getView(), "User signed in correctly", Snackbar.LENGTH_LONG);
                        navController.popBackStack();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        registerBtn = view.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(onRegisterBtnClicked);

        alreadyHaveAnAccountTextView = view.findViewById(R.id.goToLoginTextView);
        alreadyHaveAnAccountTextView.setOnClickListener(goToLoginClicked);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        navController = Navigation.findNavController(getView());
    }
}























