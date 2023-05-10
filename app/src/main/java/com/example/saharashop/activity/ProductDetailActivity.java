package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.databinding.ActivityProductDetailBinding;
import com.example.saharashop.entity.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String PRODUCT_ID = "productId";
    private Product product = new Product();
    private ActivityProductDetailBinding binding;
    private int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.quantity = 0;
        binding.txtQuantity.setText("0");
        binding.btnViewCart.setVisibility(View.GONE);
        binding.btnBackDetail.setOnClickListener(view -> finish());
        setProduct();
    }
    private void setProduct() {
        Bundle bundle = getIntent().getExtras();
        Log.d("T", bundle.getString(PRODUCT_ID));
        APIService.createService(IProductType.class).getProductById(bundle.getString(PRODUCT_ID)).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product productTemp = new Product(response.body().getStoreId(), response.body().getTypeId(), response.body().getName(),
                            response.body().getPrice(), response.body().getImage(), response.body().getDefaultImage(), response.body().getDetail(),
                            response.body().getStar(), response.body().getState());
                    product = productTemp;
                    if (product != null) {
                        setProductImage();
                        setProductTitle();
                        setProductDetail();
                        //setProductStore(productDbHelper);
                        binding.svReview.setVisibility(View.GONE);
                    }
                }
                else
                    Log.d("T", "Sai dữ liệu");
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("T", "Lỗi hệ thống");
            }
        });


    }

    private void setProductImage() {
        if (product.getImage() != null) {
            Glide.with(getApplicationContext()).load(product.getImage()).into(binding.productImage);
        }
    }
    private void setProductTitle() {
        binding.productTitle.setText(product.getName());
        binding.productPrice.setText(String.valueOf(product.getPrice()));
    }
    private void setProductDetail() {
        String productDetail = product.getDetail();
        TextView tvProductDetail = findViewById(R.id.productDescription);
        if (productDetail == null)
            tvProductDetail.setVisibility(TextView.GONE);
        else
            tvProductDetail.setText(product.getDetail());
    }
}