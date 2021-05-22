package com.example.madguidesapp.ui.sideMenu;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.databinding.FragmentProfileBinding;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.ui.dialogs.ContactWithGuideDialog;
import com.example.madguidesapp.ui.dialogs.SendBecomeAGuideSolicitude;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private User user;

    private Map<Integer, Map<String, Object>> buttonContentsMap;

    private FragmentProfileBinding binding;

    private DrawerActivityViewModel drawerActivityViewModel;
    private NavController navController;

    private final String textKey = "text", onClickKey = "onClick";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    this.user = user;
                    fillView(user);

                    if(binding.profileButton.isLoading()){
                        binding.profileButton.endLoading();
                    }
                });

        initMap();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());
    }

    public void fillView(User user){
        binding.profilePhoto.previewImageView.makeCircular();
        binding.profilePhoto.previewImageView.loadImage(user.getImageUrl());

        binding.usernameEditText.setText(user.getUsername());
        binding.emailEditText.setText(user.getEmail());

        Map<String, Object> buttonMap = buttonContentsMap.get(user.getGuideStatus());

        binding.profileButton.setText((String) buttonMap.get(textKey));
        binding.profileButton.addOnClickListener((View.OnClickListener) buttonMap.get(onClickKey));
    }

    public void initMap(){
        buttonContentsMap = new HashMap<>();

        Map<String, Object> approvedMap = new HashMap<>(),
                            notSolicitedMap = new HashMap<>(),
                            pendingMap = new HashMap<>(),
                            deniedMap = new HashMap<>();

        approvedMap.put(textKey, "Manage guide profile");
        approvedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    //navController.navigate(R.id.nav_guide_profile);
                });

        buttonContentsMap.put(User.SolicitudeStatus.APPROVED, approvedMap);

        notSolicitedMap.put(textKey, "Become a guide!");
        notSolicitedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    SendBecomeAGuideSolicitude sendBecomeAGuideSolicitude =
                            new SendBecomeAGuideSolicitude(getContext(), drawerActivityViewModel);

                    sendBecomeAGuideSolicitude.show();

                    sendBecomeAGuideSolicitude.setOnDismissListener(
                            dialog -> binding.profileButton.startLoading());
                });

        buttonContentsMap.put(User.SolicitudeStatus.NOT_SOLICITED, notSolicitedMap);

        pendingMap.put(textKey, "Guide solicitude is pending...");
        pendingMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    String msg = "Este proceso puede tardar entre 1 y 2 días, vuelva más tarde";
                    Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
                });

        buttonContentsMap.put(User.SolicitudeStatus.PENDING, pendingMap);

        deniedMap.put(textKey, "Permanently denied");
        deniedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    String msg = "Your profile doesn't meet the rules to become a guide";
                    Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
                });

        buttonContentsMap.put(User.SolicitudeStatus.DENIED, deniedMap);

    }
}











