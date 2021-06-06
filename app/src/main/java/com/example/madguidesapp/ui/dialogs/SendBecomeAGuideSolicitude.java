package com.example.madguidesapp.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;

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
