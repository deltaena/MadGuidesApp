package com.example.madguidesapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.repository.FirestoreRepository;
import com.example.madguidesapp.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SendBecomeAGuideSolicitude extends AlertDialog {

    private DrawerActivityViewModel drawerActivityViewModel;

    public SendBecomeAGuideSolicitude(
            Context context, DrawerActivityViewModel drawerActivityViewModel) {
        super(context);

        this.drawerActivityViewModel = drawerActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle(getContext().getString(R.string.becomeGuideTitle));
        setMessage(getContext().getString(R.string.becomeGuideMessage));

        setButton(BUTTON_POSITIVE, getContext().getString(R.string.ok), (dialog, which) -> {
            drawerActivityViewModel.sendBecomeAGuideSolicitude();
            cancel();
        });

        setButton(BUTTON_NEGATIVE, getContext().getString(R.string.notNow), ((dialog, which) -> dismiss()));

        super.onCreate(savedInstanceState);
    }
}
