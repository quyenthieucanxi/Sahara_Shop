package com.example.saharashop.activity;

import static com.example.saharashop.untils.AppUtilities.SELECT_PICTURE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.saharashop.R;
import com.example.saharashop.databinding.ActivityAccountInfoBinding;
import com.example.saharashop.untils.AppUtilities;

public class AccountInfoActivity extends AppCompatActivity {
    private ActivityAccountInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnChoosePhoto.setOnClickListener(AppUtilities::setChoosePhoto);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.imgAvt.setImageURI(selectedImageUri);
                }
            }
            }
        }
}

