package com.example.madguidesapp.ui.sideMenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.databinding.FragmentGuideProfileBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class GuideProfileFragment extends Fragment {

    private static final String TAG = "GuideProfileFragment";
    
    private FragmentGuideProfileBinding binding;

    private DrawerActivityViewModel drawerActivityViewModel;

    private IconButton iconButton;

    private View.OnClickListener saveGuideProfile = click -> {
        HashMap<String, Object> map = checkLinks();

        if(map.size() == 0){
            Snackbar.make(requireView(), getString(R.string.fillOneFieldToBecomeGuide), Snackbar.LENGTH_LONG).show();
            iconButton.enable();
            return;
        }

        String description = binding.descriptionEditText.getText().toString().trim();

        if(description.isEmpty()){
            Snackbar.make(requireView(), getString(R.string.fillDescriptionToBecomeGuide), Snackbar.LENGTH_LONG).show();
            iconButton.enable();
            return;
        }

        map.put("description", description);

        drawerActivityViewModel.createGuideProfile(map).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Snackbar.make(requireView(), getString(R.string.guideProfileUpdated), Snackbar.LENGTH_LONG).show();
                        iconButton.enable();
                    }
                });
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        setToolBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuideProfileBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolBar();
    }

    @Override
    public void onResume() {
        super.onResume();

        drawerActivityViewModel.getGuideLiveData().
                observe(requireActivity(), guide -> {
                    if(guide == null) return;

                    binding.instagramLinkEditText.setText(guide.getInstagram());
                    binding.twitterlinkEditText.setText(guide.getTwitter());
                    binding.linkedinLinkEditText.setText(guide.getLinkedin());
                    binding.otherLinkEditText.setText(guide.getOther());
                    binding.descriptionEditText.setText(guide.getDescription());
                });
    }

    private void setToolBar(){
        Toolbar toolbar = ((AppCompatActivity)requireActivity()).findViewById(R.id.toolbar);

        iconButton = toolbar.findViewById(R.id.toolbarRightButton);
        iconButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.checked_icon));
        iconButton.setVisibility(View.VISIBLE);
        iconButton.addListener(saveGuideProfile);
    }

    public HashMap<String, Object> checkLinks(){
        PackageManager packageManager = requireActivity().getPackageManager();

        HashMap<String, Object> map = new HashMap<>();

        String instagram = binding.instagramLinkEditText.getText().toString().trim(),
                twitter = binding.twitterlinkEditText.getText().toString().trim(),
                linkedin = binding.linkedinLinkEditText.getText().toString().trim(),
                other = binding.otherLinkEditText.getText().toString().trim();

        if(!instagram.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
            if(packageManager.resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
                map.put("instagram", instagram);
            }
            else{
                binding.instagramLinkEditText.setError("Introduzca un enlace válido");
            }
        }

        if(!twitter.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
            if(packageManager.resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
                map.put("twitter", twitter);
            }
            else{
                binding.twitterlinkEditText.setError("Introduzca un enlace válido");
            }

        }

        if(!linkedin.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedin));
            if(packageManager.resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
                map.put("linkedin", linkedin);
            }
            else{
                binding.linkedinLinkEditText.setError("Introduzca un enlace válido");
            }


        }

        if(!other.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(other));
            if(packageManager.resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
                map.put("other", other);
            }
            else{
                binding.otherLinkEditText.setError("Introduzca un enlace válido");
            }

        }

        return map;
    }
}

















