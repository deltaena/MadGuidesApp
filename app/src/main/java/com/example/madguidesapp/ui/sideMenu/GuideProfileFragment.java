package com.example.madguidesapp.ui.sideMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.databinding.FragmentGuideProfileBinding;
import com.example.madguidesapp.pojos.Guide;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

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
        HashMap<String, Object> map = new HashMap<>();

        String instagram = binding.instagramLinkEditText.getText().toString().trim(),
                twitter = binding.twitterlinkEditText.getText().toString().trim(),
                linkedin = binding.linkedinLinkEditText.getText().toString().trim(),
                other = binding.otherLinkEditText.getText().toString().trim();

        if(!instagram.isEmpty()) map.put("instagram", instagram);

        if(!twitter.isEmpty()) map.put("twitter", twitter);

        if(!linkedin.isEmpty()) map.put("linkedin", linkedin);

        if(!other.isEmpty()) map.put("other", other);

        return map;
    }
}

















