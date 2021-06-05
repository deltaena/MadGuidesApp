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

        builder.setTitle(getString(R.string.accountRequired));
        builder.setMessage(getString(R.string.askForSignUp));

        builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.popBackStack(R.id.nav_main_menu, false);
            navController.navigate(R.id.nav_login);
            dismiss();
        });

        builder.setNegativeButton(getString(R.string.notNow), ((dialog, which) -> dismiss() ));

        return builder.create();
    }


}


















