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
import com.example.madguidesapp.databinding.FragmentRegisterBinding;
import com.example.madguidesapp.pojos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterFragment extends ConnectivityFragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    private FragmentRegisterBinding binding;

    private NavController navController;

    View.OnClickListener onRegisterBtnClicked = click -> {
        Map<String, String> userDataChecked = checkUserData();

        if(userDataChecked == null) {
            binding.registerBtn.endLoading();
            return;
        }

        OnCompleteListener<QuerySnapshot> onUsernameChecked = task -> {
            if(task.isSuccessful()){
                if(task.getResult().size() == 0) {
                    Snackbar.make(requireView(), getString(R.string.signedUp), Snackbar.LENGTH_LONG).show();

                    register(userDataChecked);
                }
                else{
                    binding.usernameEditText.setError(getString(R.string.usernameExistsAlready));
                    binding.registerBtn.endLoading();
                }
            }
        };

        drawerActivityViewModel.usernameExists(userDataChecked.get("username")).
                addOnCompleteListener(onUsernameChecked);
    };

    private void register(Map<String, String> userDataChecked) {
        User user = new User();

        user.setEmail(userDataChecked.get("email"));
        user.setUsername(userDataChecked.get("username"));
        user.setGuideStatus(User.SolicitudeStatus.NOT_SOLICITED);

        drawerActivityViewModel.
                register(user, userDataChecked.get("password")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Snackbar.make(getView(), getString(R.string.signedUp), Snackbar.LENGTH_LONG).show();
                        navController.popBackStack();
                    }
                    else{
                        Snackbar.make(getView(), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                        binding.registerBtn.endLoading();
                    }
                });
    }

    View.OnClickListener goToLoginClicked = click -> {
        String emailStr = binding.emailEditText.getText(),
                passwordStr = binding.passwordEditText.getText();

        Bundle bundle = new Bundle();
        bundle.putString("email", emailStr);
        bundle.putString("password", passwordStr);

        navController.popBackStack();
        navController.navigate(R.id.nav_login, bundle);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);

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

        binding.registerBtn.addOnClickListener(onRegisterBtnClicked);

        binding.goToLoginTextView.setOnClickListener(goToLoginClicked);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private Map<String, String> checkUserData(){
        Map<String, String> dataMap = new HashMap<>();
        Pattern matchOneLowerCase = Pattern.compile("[a-z]+"),
                matchOneUpperCase = Pattern.compile("[A-Z]+"),
                matchOneNumber = Pattern.compile("[0-9]+"),
                matchOneSpecialCharacter = Pattern.compile("[^A-Za-z0-9]");


        String email = binding.emailEditText.getText().trim().toLowerCase();
        String password = binding.passwordEditText.getText();
        String username = binding.usernameEditText.getText();

        if(email.isEmpty()){
            binding.emailEditText.setError(getString(R.string.requiredField));
            return null;
        }

        if(!matchOneLowerCase.matcher(password).find()){
            binding.passwordEditText.setError(getString(R.string.invalidPasswordBase)+getString(R.string.lowerCase));
            return null;
        }

        if(!matchOneUpperCase.matcher(password).find()){
            binding.passwordEditText.setError(getString(R.string.invalidPasswordBase)+getString(R.string.upperCase));
            return null;
        }

        if(!matchOneNumber.matcher(password).find()){
            binding.passwordEditText.setError(getString(R.string.invalidPasswordBase)+getString(R.string.number));
            return null;
        }

        if(!matchOneSpecialCharacter.matcher(password).find()){
            binding.passwordEditText.setError(getString(R.string.invalidPasswordBase)+getString(R.string.specialChar));
            return null;
        }

        if(password.length() < 8){
            binding.passwordEditText.setError(getString(R.string.invalidPasswordBase)+getString(R.string.eightChars));
            return null;
        }

        if(username.isEmpty()){
            binding.usernameEditText.setError(getString(R.string.requiredField));
            return null;
        }

        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("username", username);
        return dataMap;
    }

}























