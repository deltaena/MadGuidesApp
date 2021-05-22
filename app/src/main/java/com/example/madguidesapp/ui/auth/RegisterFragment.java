package com.example.madguidesapp.ui.auth;

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

import com.example.madguidesapp.android.connectivity.ConnectivityFragment;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.databinding.FragmentRegisterBinding;
import com.example.madguidesapp.pojos.User;
import com.google.android.material.snackbar.Snackbar;

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

        drawerActivityViewModel.usernameExists(userDataChecked.get("username")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(task.getResult().size() == 0) {
                            Snackbar.make(getView(), "Usuario registrado con exito!", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            binding.usernameEditText.setError("El nombre de usuario ya está en uso");
                        }
                    }
                });

        User user = new User();

        user.setEmail(userDataChecked.get("email"));
        user.setUsername(userDataChecked.get("username"));
        user.setGuideStatus(User.SolicitudeStatus.NOT_SOLICITED);

        drawerActivityViewModel.
                register(user, userDataChecked.get("password")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Snackbar.make(getView(), "Usuario registrado con éxito", Snackbar.LENGTH_LONG).show();
                        navController.popBackStack();
                    }
                    else{
                        Snackbar.make(getView(), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    };

    View.OnClickListener goToLoginClicked = click -> {
        String emailStr = binding.emailEditText.getText().toString(),
                passwordStr = binding.passwordEditText.getText().toString();

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
        //email no es vacío
        /*
            At least 8 characters—the more characters, the better
            A mixture of both uppercase and lowercase letters
            A mixture of letters and numbers
            Inclusion of at least one special character, e.g., ! @ # ? ]
            Note: do not use < or > in your password, as both can cause problems in Web browsers
         */

        Map<String, String> dataMap = new HashMap<>();
        Pattern matchOneLowerCase = Pattern.compile("[a-z]+"),
                matchOneUpperCase = Pattern.compile("[A-Z]+"),
                matchOneNumber = Pattern.compile("[0-9]+"),
                matchOneSpecialCharacter = Pattern.compile("[^A-Za-z0-9]");


        String email = binding.emailEditText.getText().toString().trim().toLowerCase();
        String password = binding.passwordEditText.getText().toString();
        String username = binding.usernameEditText.getText().toString();

        if(email.isEmpty()){
            binding.emailEditText.setError("Campo obligatorio");
            return null;
        }

        String basePasswordErrorText = "La contraseña debe contener al menos ";

        if(!matchOneLowerCase.matcher(password).find()){
            binding.passwordEditText.setError(basePasswordErrorText+"una minúscula");
            return null;
        }

        if(!matchOneUpperCase.matcher(password).find()){
            binding.passwordEditText.setError(basePasswordErrorText+"una mayuscula");
            return null;
        }

        if(!matchOneNumber.matcher(password).find()){
            binding.passwordEditText.setError(basePasswordErrorText+"un número");
            return null;
        }

        if(!matchOneSpecialCharacter.matcher(password).find()){
            binding.passwordEditText.setError(basePasswordErrorText+"un carácter especial");
            return null;
        }

        if(password.length() < 8){
            binding.passwordEditText.setError(basePasswordErrorText+"8 cáracteres");
            return null;
        }

        if(username.isEmpty()){
            binding.usernameEditText.setError("Campo oblligatorio");
            return null;
        }

        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("username", username);
        return dataMap;
    }

}























