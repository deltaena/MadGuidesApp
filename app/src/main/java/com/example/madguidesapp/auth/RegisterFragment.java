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
import com.example.madguidesapp.pojos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    private Button registerBtn;

    private TextView alreadyHaveAnAccountTextView;

    private EditText emailEditText, usernameEditText, passwordEditText;

    private NavController navController;

    View.OnClickListener onRegisterBtnClicked = click -> {
        Map<String, String> userDataChecked = checkUserData();

        if(userDataChecked == null) return;

        drawerActivityViewModel.usernameExists(userDataChecked.get("username")).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(task.getResult().size() == 0) {
                            Snackbar.make(getView(), "Usuario registrado con exito!", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            usernameEditText.setError("El nombre de usuario ya está en uso");
                        }
                    }
                });

        User user = new User();

        user.setEmail(userDataChecked.get("email"));
        user.setUsername(userDataChecked.get("username"));

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
        String emailStr = emailEditText.getText().toString(),
                passwordStr = passwordEditText.getText().toString();

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        if(getArguments() != null){
            emailEditText.setText(getArguments().getString("email", ""));
            passwordEditText.setText(getArguments().getString("password", ""));
        }

        registerBtn = view.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(onRegisterBtnClicked);

        alreadyHaveAnAccountTextView = view.findViewById(R.id.goToLoginTextView);
        alreadyHaveAnAccountTextView.setOnClickListener(goToLoginClicked);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());
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


        String email = emailEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString();
        String username = usernameEditText.getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Campo obligatorio");
            return null;
        }

        String basePasswordErrorText = "La contraseña debe contener al menos ";

        if(!matchOneLowerCase.matcher(password).find()){
            passwordEditText.setError(basePasswordErrorText+"una minúscula");
            return null;
        }

        if(!matchOneUpperCase.matcher(password).find()){
            passwordEditText.setError(basePasswordErrorText+"una mayuscula");
            return null;
        }

        if(!matchOneNumber.matcher(password).find()){
            passwordEditText.setError(basePasswordErrorText+"un número");
            return null;
        }

        if(!matchOneSpecialCharacter.matcher(password).find()){
            passwordEditText.setError(basePasswordErrorText+"un carácter especial");
            return null;
        }

        if(password.length() < 8){
            passwordEditText.setError(basePasswordErrorText+"8 cáracteres");
            return null;
        }

        if(username.isEmpty()){
            usernameEditText.setError("Campo oblligatorio");
            return null;
        }

        dataMap.put("email", email);
        dataMap.put("password", password);
        dataMap.put("username", username);
        return dataMap;
    }

}























