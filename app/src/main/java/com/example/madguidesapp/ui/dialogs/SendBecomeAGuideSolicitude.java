package com.example.madguidesapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.repository.FirestoreRepository;
import com.example.madguidesapp.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SendBecomeAGuideSolicitude extends AlertDialog {

    private FirestoreRepository firestoreRepository;
    private UserRepository userRepository;
    private DrawerActivityViewModel drawerActivityViewModel;

    public SendBecomeAGuideSolicitude(
            Context context, DrawerActivityViewModel drawerActivityViewModel) {
        super(context);

        this.drawerActivityViewModel = drawerActivityViewModel;
        firestoreRepository = new FirestoreRepository();
        userRepository = new UserRepository();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Send become a guide solicitude");
        setMessage("By clicking ok you accept to send your profile to revision and "+
                "collaborate to create a better community if you are approved to "+
                "exert as a guide.");

        setButton(BUTTON_POSITIVE, "Ok!", (dialog, which) -> {
            drawerActivityViewModel.sendBecomeAGuideSolicitude();
            cancel();
        });

        setButton(BUTTON_NEGATIVE, "Not now", ((dialog, which) -> dismiss()));

        super.onCreate(savedInstanceState);
    }
}
