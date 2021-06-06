package com.example.madguidesapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.connectivity.ConnectivityFragment;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends ConnectivityFragment {

    private FragmentLoginBinding binding;

    private DrawerActivityViewModel drawerActivityViewModel;

    private NavController navController;

    View.OnClickListener onLoginBtnClicked = click -> {
        Map<String, String> userDataChecked = checkUserData();

        if(userDataChecked == null){
            binding.loginBtn.endLoading();
            return;
        }

        drawerActivityViewModel.signIn(
                userDataChecked.get("email"), userDataChecked.get("password")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Snackbar.make(getView(), getContext().getString(R.string.signedIn), Snackbar.LENGTH_LONG).show();
                        navController.popBackStack();
                    }
                    else{
                        Snackbar.make(getView(), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    }

                    binding.loginBtn.endLoading();
                });
    };

    View.OnClickListener goToRegisterClicked = click -> {
        String emailStr = binding.emailEditText.getText(),
                passwordStr = binding.passwordEditText.getText();

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
        binding = FragmentLoginBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());

        if(getArguments() != null){
            binding.emailEditText.setText(getArguments().getString("email", ""));
            binding.passwordEditText.setText(getArguments().getString("password", ""));
        }

        binding.loginBtn.addOnClickListener(onLoginBtnClicked);

        binding.goToRegisterTextView.setOnClickListener(goToRegisterClicked);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private Map<String, String> checkUserData(){
        Map<String, String> dataMap = new HashMap<>();

        String email = binding.emailEditText.getText();
        String password = binding.passwordEditText.getText();

        if(email.isEmpty()){
            binding.emailEditText.setError(getString(R.string.requiredField));
            return null;
        }

        if(password.isEmpty()){
            binding.passwordEditText.setError(getString(R.string.requiredField));
        }

        dataMap.put("email", email);
        dataMap.put("password", password);
        return dataMap;
    }
}



















