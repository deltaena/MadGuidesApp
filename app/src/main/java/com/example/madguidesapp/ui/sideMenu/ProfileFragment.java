package com.example.madguidesapp.ui.sideMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.databinding.FragmentProfileBinding;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.ui.dialogs.SendBecomeAGuideSolicitude;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    private Map<Integer, Map<String, Object>> buttonContentsMap;

    private FragmentProfileBinding binding;

    private DrawerActivityViewModel drawerActivityViewModel;
    private NavController navController;

    private IconButton iconButton;

    private final String textKey = "text", onClickKey = "onClick";

    private View.OnClickListener updateUser = click -> {

        User userChanged = setChanges();

        if(userChanged != null) drawerActivityViewModel.updateUser(userChanged);
        else {
            Snackbar.make(requireView(), getString(R.string.noChanges), Snackbar.LENGTH_SHORT).show();
            iconButton.enable();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        navController = Navigation.findNavController(requireView());
    }

    public void fillView(User user){
        binding.profilePhoto.previewImageView.makeCircular();
        binding.profilePhoto.previewImageView.loadImage(user.getImageUrl());

        binding.usernameEditText.setText(user.getUsername());
        binding.addresEditText.setText(user.getAddress());

        Map<String, Object> buttonMap = buttonContentsMap.get(user.getGuideStatus());

        assert buttonMap != null;
        binding.profileButton.setText((String) buttonMap.get(textKey));
        binding.profileButton.addOnClickListener((View.OnClickListener) buttonMap.get(onClickKey));

        binding.changeProfilePhotoTextView.
                setOnClickListener(v -> ImagePicker.Companion.with(this).start());

        setToolBar();
    }

    public void initMap(){
        buttonContentsMap = new HashMap<>();

        Map<String, Object> approvedMap = new HashMap<>(),
                            notSolicitedMap = new HashMap<>(),
                            pendingMap = new HashMap<>(),
                            deniedMap = new HashMap<>();

        approvedMap.put(textKey, getString(R.string.manageGuideProfile));
        approvedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    navController.navigate(R.id.nav_guide_profile);
                });

        buttonContentsMap.put(User.SolicitudeStatus.APPROVED, approvedMap);

        notSolicitedMap.put(textKey, getString(R.string.becomeGuide));
        notSolicitedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    SendBecomeAGuideSolicitude sendBecomeAGuideSolicitude =
                            new SendBecomeAGuideSolicitude(getContext(), drawerActivityViewModel);

                    sendBecomeAGuideSolicitude.show();

                    sendBecomeAGuideSolicitude.setOnCancelListener(
                            dialog -> binding.profileButton.startLoading());
                });

        buttonContentsMap.put(User.SolicitudeStatus.NOT_SOLICITED, notSolicitedMap);

        pendingMap.put(textKey, getString(R.string.guideSolicitudePending));
        pendingMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    Snackbar.make(requireView(), getString(R.string.guideSolicitudePendingWarning), Snackbar.LENGTH_LONG).show();
                });

        buttonContentsMap.put(User.SolicitudeStatus.PENDING, pendingMap);

        deniedMap.put(textKey, getString(R.string.permanentlyDenied));
        deniedMap.put(onClickKey,
                (View.OnClickListener) v -> {
                    String msg = getString(R.string.permanentlyDeniedWarning);
                    Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show();
                });

        buttonContentsMap.put(User.SolicitudeStatus.DENIED, deniedMap);
    }
    private void setToolBar(){
        Toolbar toolbar = ((AppCompatActivity)requireActivity()).findViewById(R.id.toolbar);

        iconButton = toolbar.findViewById(R.id.toolbarRightButton);
        iconButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.checked_icon));
        iconButton.setVisibility(View.VISIBLE);
        iconButton.addListener(updateUser);
    }

    private User setChanges(){
        boolean hasChanges = false;
        User user = drawerActivityViewModel.getUserLiveData().getValue();

        String username = binding.usernameEditText.getText().toString().trim();
        if(!user.getUsername().equals(username)) hasChanges = true;

        user.setUsername(username);

        String address = binding.addresEditText.getText().toString();
        if(!user.getAddress().equals(address)) hasChanges = true;

        user.setAddress(address);

        return hasChanges ? user : null;
    }

    @Override
    public void onResume() {
        super.onResume();

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getUserLiveData().
                observe(this, user -> {
                    fillView(user);

                    if(binding.profileButton.isLoading()){
                        binding.profileButton.endLoading();
                    }

                    binding.changeProfilePhotoTextView.setClickable(true);
                    binding.profilePhotoProgressBar.setVisibility(GONE);

                    iconButton.enable();
                });

        setToolBar();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            assert data != null;
            drawerActivityViewModel.updateProfilePhoto(data.getData());
            binding.changeProfilePhotoTextView.setClickable(false);
            binding.profilePhotoProgressBar.setVisibility(View.VISIBLE);
        }
    }
}











