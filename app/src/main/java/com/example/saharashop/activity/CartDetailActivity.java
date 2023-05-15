package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.ICartService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.api.IStore;
import com.example.saharashop.databinding.ActivityCartDetailBinding;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.entity.Product;

import com.example.saharashop.entity.User;

import com.example.saharashop.entity.Store;

import com.example.saharashop.fragment.CartFragment;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartDetailActivity extends AppCompatActivity {
    public static Cart cart;
    private Product product;
    private int quantity = 0;
    private ActivityCartDetailBinding binding;
    private CartFragment cartFragment = new CartFragment();
    private User user = SharedPrefManager.getInstance(this).getUser();

    
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
        binding.btnCancelOrder.setOnClickListener(this::setCancelOrder);
        binding.btnOrder.setOnClickListener(this::Order);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("frame_container_id", MainActivity.FRAME_CONTAINER_ID);
    }

    private void addNotification(){
        Notification notification = new Notification(user.getId(), "Bạn đã đặt "+quantity+" " + product.getName()+" thành công!", true);
        APIService.createService(INotification.class).add(notification).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBill(){
        Bill bill = new Bill(user.getId(), cart.getId(), user.getPhone(), user.getAddress(), product.getName(), quantity, product.getPrice());
        APIService.createService(IBillService.class).add(bill).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("state","Paid");
                Toast.makeText(getApplicationContext(), "Thêm bill thành công!", Toast.LENGTH_SHORT).show();
                updateStateCard(bill.getCartId(),map);
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateStateCard(String idCard,Map<String,String> state){
        APIService.createService(ICartService.class).updateState(idCard,state).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Thay đổi state thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Thay đổi state thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Order(View view) {
        try {
            if (quantity == 0)
                return;
            addNotification();
            addBill();
            Intent intent = new Intent(CartDetailActivity.this, MainActivity.class);
            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
        }

    }
    private void setCancelOrder(@NotNull View view) {
        APIService.createService(ICartService.class).delete(cart.getId()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Cancel thất bại", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(view.getContext(), "Đã xóa khỏi giỏ hàng.", Toast.LENGTH_SHORT).show();
                    finish();
                    /*
                    int frameContainerId = getIntent().getIntExtra("frame_container_id", -1);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(frameContainerId, cartFragment);
                    transaction.commit();*/
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(CartDetailActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });


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
                    getStoreByProductId(product);
                }
                else
                    Log.d("T", "Sai dữ liệu, Lỗi!");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("T", "Lỗi hệ thống");
            }
        });
        setAddress();
        setQuantity();
    }
    private void getStoreByProductId(Product product)
    {
        APIService.createService(IStore.class).getStoreByProductId(product.getId()).enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                if (response.isSuccessful()) {
                    Store store = response.body();
                    if (store == null)
                        return;
                    binding.productStore.setText(store.getName());
                    binding.productStoreAddress.setText(store.getAddress());
                }
                else
                    Log.d("T", "Sai dữ liệu");
            }
            @Override
            public void onFailure(Call<Store> call, Throwable t) {
            }
        });
    }

    private void setAddress() {

        String address = SharedPrefManager.getInstance(getApplicationContext()).getUser().getAddress();
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