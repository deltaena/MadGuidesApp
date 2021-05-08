package com.example.madguidesapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.madguidesapp.R;

public class AccountRequiredDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Account required");
        builder.setMessage("You must be signed in in order to use " +
                "this feature, would you like to sign in now?");

        builder.setPositiveButton("Yes!", (dialog, which) -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_login);
            dismiss();
        });

        builder.setNegativeButton("Not now", ((dialog, which) -> dismiss() ));

        return builder.create();
    }


}


















