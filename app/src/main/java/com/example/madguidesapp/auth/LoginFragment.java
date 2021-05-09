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

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    private EditText emailEditText, passwordEditText;

    private NavController navController;

    View.OnClickListener onLoginBtnClicked = click -> {
        Map<String, String> userDataChecked = checkUserData();

        if(userDataChecked == null){
            return;
        }

        drawerActivityViewModel.signIn(
                userDataChecked.get("email"), userDataChecked.get("password")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Snackbar.make(getView(), "Sesión iniciada correctamente", Snackbar.LENGTH_LONG).show();
                        navController.popBackStack();
                    }
                    else{
                        Snackbar.make(getView(), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    };

    View.OnClickListener goToRegisterClicked = click -> {
        String emailStr = emailEditText.getText().toString(),
                passwordStr = passwordEditText.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("email", emailStr);
        bundle.putString("password", passwordStr);

        navController.popBackStack();
        navController.navigate(R.id.nav_register, bundle);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        if(getArguments() != null){
            emailEditText.setText(getArguments().getString("email", ""));
            passwordEditText.setText(getArguments().getString("password", ""));
        }

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

    private Map<String, String> checkUserData(){
        //email no es vacío

        Map<String, String> dataMap = new HashMap<>();

        String email = emailEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Campo obligatorio");
            return null;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Campo obligatorio");
        }

        dataMap.put("email", email);
        dataMap.put("password", password);
        return dataMap;
    }
}



















