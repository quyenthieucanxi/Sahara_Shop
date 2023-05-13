package com.example.saharashop.activity;

import static com.example.saharashop.untils.AppUtilities.SELECT_PICTURE;
import static com.example.saharashop.untils.AppUtilities.TAKE_PICTURE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.databinding.LoginBinding;
import com.example.saharashop.databinding.SignupBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.AppUtilities;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context ctx = this;
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnSignUp.setOnClickListener(view -> handleSignUp());
        binding.txtSignIn.setOnClickListener(view -> setLogIn());
        binding.btnChoosePhoto.setOnClickListener(AppUtilities::setChoosePhoto);
        setConfirmPassword();
    }
    private void setConfirmPassword() {
        binding.txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setConfirmPasswordErrorHelper();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setConfirmPasswordErrorHelper();
            }
        });
    }
    private void setConfirmPasswordErrorHelper() {
        String password = binding.txtPassword.getText().toString();
        String confirmPassword = binding.txtConfirmPassword.getText().toString();

        if (password.equals(confirmPassword))
            binding.layoutConfirmPassword.setErrorEnabled(false);
        else {
            binding.layoutConfirmPassword.setErrorEnabled(true);
            binding.layoutConfirmPassword.setError("Phải trùng với mật khẩu đã nhập.");
        }
    }
    void setLogIn() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    private boolean validate(@NotNull String fullName, String email, String phone, String address,
                             String username, String password, String confirmPassword) {
        if (fullName.equals("")) return false;
        if (email.equals("")) return false;
        if (phone.equals("")) return false;
        if (address.equals("")) return false;
        if (username.equals("")) return false;
        if (password.equals("")) return false;
        if (confirmPassword.equals("")) return false;
        if (!password.equals(confirmPassword)) return false;
        return password.length() >= 6;
    }

    @NotNull
    private String getSex() {
        int selected = binding.chipGroupSex.getCheckedChipId();
        switch (selected) {
            case R.id.chipMale:
                return "Male";
            case R.id.chipFemale:
                return "Female";
            default:
                return "Orther";
        }
    }

    private void addUser(User user){
        APIService.createService(IAuthService.class).addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() != 200) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Đăng ký thành công: " + response.body().getAccountId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, Login.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi trong quá trình tạo tài khoản. Vui lòng tạo lại!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAccount(Account1 account1, String valueFullName, String valuePhone, String valueAddress, String valueConfirmPassword){
        APIService.createService(IAuthService.class).signUp(account1).enqueue(new Callback<Account1>() {
            String valueAccountId;
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if (response.code() != 200) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                valueAccountId = response.body().getId();
                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công thành công: " + response.body().getId(), Toast.LENGTH_SHORT).show();

                User user = new User(valueAccountId, valueFullName, getSex(), valuePhone, valueAddress, valueConfirmPassword, true);
                addUser(user);
            }
            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại 11", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSignUp() {
        String valueFullName = binding.txtFullName.getText().toString();
        String valueUsername = binding.txtUsername.getText().toString();
        String valueAddress = binding.txtAddress.getText().toString();
        String valuePhone = binding.txtPhone.getText().toString();
        String valueEmail = binding.txtEmail.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(valueEmail).matches()) {
            // Nếu email không đúng định dạng, hiển thị thông báo và focus vào ô email
            binding.txtEmail.setError("Email không hợp lệ");
            binding.txtEmail.requestFocus();
            return;
        }
        String valuePassword = binding.txtPassword.getText().toString();
        String valueConfirmPassword = binding.txtConfirmPassword.getText().toString();

        if (!validate(valueFullName, valueEmail, valuePhone, valueAddress, valueUsername, valuePassword, valueConfirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin cá nhân!", Toast.LENGTH_SHORT).show();
            return;
        }
        Account1 account = new Account1(valueUsername, valueEmail, valuePassword, "user", true);

        addAccount(account, valueFullName, valuePhone, valueAddress, valueConfirmPassword);

    }
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
            } //else  (requestCode == TAKE_PICTURE) {
                //setPic(imgAvt);
            }
        } //else if (resultCode == FirebaseActivity.CREATE_ACCOUNT_OK) {
//            if (requestCode == FirebaseActivity.CREATE_ACCOUNT) {
//                Toast.makeText(this, "Đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
        //}
    }


