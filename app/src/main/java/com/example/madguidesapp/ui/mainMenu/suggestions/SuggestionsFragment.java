package com.example.madguidesapp.ui.mainMenu.suggestions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.Suggestion;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Date;

public class SuggestionsFragment extends Fragment {

    private static final String TAG = "SuggestionsFragment";
    
    private DrawerActivityViewModel drawerActivityViewModel;
    private EditText suggestionEditText;
    private ImageView attachmentImageView;
    private CheckBox checkBox;
    private ProgressBar suggestionPendingProgressBar;
    private Button sendSuggestionButton;

    private Uri suggestionFileUri;

    private Suggestion suggestion;

    private OnCompleteListener<UploadTask.TaskSnapshot> onImageUploaded = task -> {
        task.getResult().getMetadata().getReference().getDownloadUrl().
                addOnCompleteListener(task1 -> {
                    suggestion.setAttachmentUrl(task1.getResult().toString());

                    setFieldsAndUploadSuggestion();
                });
    };

    private View.OnClickListener onSendSuggestionClicked = v -> {
        String suggestionText = suggestionEditText.getText().toString().trim();

        if(suggestionText.isEmpty()){
            suggestionEditText.setError(getString(R.string.requiredField));
            return;
        }

        sendSuggestionButton.setVisibility(View.INVISIBLE);

        suggestion = new Suggestion();

        if(checkBox.isChecked()){
            drawerActivityViewModel.
                    uploadSuggestionImage(suggestionFileUri).
                    addOnCompleteListener(onImageUploaded);
        }
        else{
            setFieldsAndUploadSuggestion();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getAreAllOperationsDoneLiveData().
                observe(this, aBoolean -> {
                    if(aBoolean == null) return;

                    if(aBoolean) {
                        suggestionPendingProgressBar.setVisibility(View.GONE);
                        sendSuggestionButton.setVisibility(View.VISIBLE);
                        Snackbar.make(getView(), getString(R.string.suggestionSended), Snackbar.LENGTH_LONG).show();
                        suggestionEditText.setText("");
                        checkBox.setChecked(false);
                        drawerActivityViewModel.suggestionProcessed();
                    }
                    else{
                        suggestionPendingProgressBar.setVisibility(View.VISIBLE);
                        sendSuggestionButton.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        suggestionEditText = view.findViewById(R.id.suggestionEditText);

        attachmentImageView = view.findViewById(R.id.suggestionImageView);

        checkBox = view.findViewById(R.id.includeImageCheckBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                if(attachmentImageView.getDrawable() == null){
                    checkBox.setChecked(false);

                    ImagePicker.Companion.
                            with(this).
                            start();
                }
            }
            else{
                suggestionFileUri = null;
                attachmentImageView.setImageDrawable(null);
            }
        });

        sendSuggestionButton = view.findViewById(R.id.sendSuggestionbutton);
        sendSuggestionButton.setOnClickListener(onSendSuggestionClicked);

        suggestionPendingProgressBar = view.findViewById(R.id.suggestionPendingProgressBar);

        return view;
    }

    public void setFieldsAndUploadSuggestion(){
        String suggestionText = suggestionEditText.getText().toString().trim();

        suggestion.setSuggestionText(suggestionText);
        suggestion.setCreationDate(new Date());

        if(drawerActivityViewModel.areUserRegistered()) {
            suggestion.setSenderEmail(drawerActivityViewModel.getUserLiveData().getValue().getEmail());
        }
        else{
            suggestion.setSenderEmail("");
        }

        drawerActivityViewModel.uploadSuggestion(suggestion);
        suggestion = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            Dexter.withContext(getContext()).
                    withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                    withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            suggestionFileUri = Uri.parse(data.getDataString());

                            attachmentImageView.setImageURI(suggestionFileUri);

                            checkBox.setChecked(true);
                        }
                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            String msg = "Permiso para acceder al almacenamiento ";

                            if(permissionDeniedResponse.isPermanentlyDenied()){
                                msg += "permanentemente ";
                            }

                            msg += "denegado";

                            Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).
                    check();
        }
    }
}












