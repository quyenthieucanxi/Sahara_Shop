package com.example.saharashop.activity;

import static com.example.saharashop.untils.AppUtilities.SELECT_PICTURE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IUserService;
import com.example.saharashop.databinding.ActivityAccountInfoBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.AppUtilities;
import com.example.saharashop.untils.RealPathUtil;
import com.example.saharashop.untils.SharedPrefManager;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoActivity extends AppCompatActivity {
    private ActivityAccountInfoBinding binding;
    private static Uri mUri;
    private User user = SharedPrefManager.getInstance(this).getUser();
    private Account1 account1 = SharedPrefManager.getInstance(this).getAccount();
    private String idUser = SharedPrefManager.getInstance(this).getIdUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnChoosePhoto.setOnClickListener(AppUtilities::setChoosePhoto);

        setAccountInfor();

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaaaaaaaaaaaaa", "aaaaaaaa");
                handleUpdate();
            }
        });
    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }

    private void updateAccount(String idAccout, Account1 newAccount){
        APIService.createService(IUserService.class).updateAccount(idAccout, newAccount).enqueue(new Callback<Account1>() {
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Cập nhật account thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống 4", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String idUser, User newUser){
        String email = binding.txtEmail.getText().toString().trim();
        Account1 newAccount = new Account1(email);
        APIService.createService(IUserService.class).updateUser(idUser, newUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateAccount(account1.getId(), newAccount);
                Toast.makeText(getApplicationContext(), "Cập nhật user thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống 2", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleUpdate() {
        String fullname = binding.txtFullName.getText().toString().trim();
        String address = binding.txtAddress.getText().toString().trim();
        String phone = binding.txtPhone.getText().toString().trim();
        User newUser = new User(fullname, phone, address, "abc");
        updateUser(user.getId(), newUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                mUri = selectedImageUri;
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.imgAvt.setImageURI(selectedImageUri);
                }
            }
            }
    }

    private void setAccountInfor(){
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            //binding.menuAvatar.setImageBitmap(user.getAvatar());
            binding.txtFullName.setText(user.getFullname());
            binding.txtAddress.setText(user.getAddress());
            binding.txtEmail.setText(account1.getEmail());
            binding.txtPhone.setText(user.getPhone());
        }
    }
}

