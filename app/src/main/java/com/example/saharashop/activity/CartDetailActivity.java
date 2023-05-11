package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.databinding.ActivityCartDetailBinding;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.entity.Product;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailActivity extends AppCompatActivity {
    public static Cart cart;
    private Product product;
    private int quantity = 0;
    private ActivityCartDetailBinding binding;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (cart == null) return;
        binding.btnBack.setOnClickListener(view -> finish());
        setCartInfo();
        binding.plus.setOnClickListener(this::setPlus);
        binding.subtract.setOnClickListener(this::setSubtract);
    }
    private void setCartInfo() {
        if (cart == null)
            return;
        APIService.createService(IProductType.class).getProductById(cart.getProductId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    product = response.body();
                    binding.productTitle.setText(product.getName());
                    binding.productPrice.setText(String.valueOf(product.getPrice()));
                    binding.cartPrice.setText(String.valueOf(cart.getQuantity()*product.getPrice()));
                    Glide.with(getApplicationContext()).load(product.getImage()).into(binding.productImage);
                }
                else
                    Log.d("T", "Sai dữ liệu");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("T", "Lỗi hệ thống");
            }
        });


        setAddress();
        setQuantity();

        /*if (store == null)
            return;
        binding.txtProductStore.setText(store.getName());
        binding.txtProductStoreAddress.setText(store.getAddress());*/
    }

    private void setAddress() {
        //String address = user.getAddress();
        String address = "TP.HCM";
        if (address.length() > 0) {
            binding.txtDeliveryAddress.setText(address);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setQuantity() {
        this.quantity = cart.getQuantity();
        binding.txtQuantity.setText(cart.getQuantity().toString());
        binding.txtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.txtQuantity.getText().equals("0"))
                    binding.txtQuantity.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.txtQuantity.getText().length() <= 0)
                    binding.txtQuantity.setText("0");
                calcPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                quantity = Integer.parseInt(binding.txtQuantity.getText().toString());
                calcPrice();
            }
        });
    }
    private void calcPrice() {
        String quantity = binding.txtQuantity.getText().toString();
        double totalPrice = product.getPrice() * Integer.valueOf(quantity);
        binding.cartPrice.setText(String.valueOf(totalPrice));
    }

    private void setSubtract(View view) {
        if (quantity <= 0) return;
        this.quantity--;
        binding.txtQuantity.setText(String.valueOf(quantity));
    }

    private void setPlus(View view) {
        this.quantity++;
        binding.txtQuantity.setText(String.valueOf(quantity));
    }

}