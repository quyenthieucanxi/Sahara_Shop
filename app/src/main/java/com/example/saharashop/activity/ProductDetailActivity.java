package com.example.saharashop.activity;

import static com.example.saharashop.untils.SharedPrefManager.getIdUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saharashop.R;
import com.example.saharashop.adapter.BillAdapter;
import com.example.saharashop.adapter.ReviewAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.ICartService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.api.IStore;
import com.example.saharashop.databinding.ActivityProductDetailBinding;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.entity.Product;
import com.example.saharashop.entity.Review;
import com.example.saharashop.entity.Store;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    public static final int GET_PRODUCT_LOVE = 2;
    public static final int GET_PRODUCT_LOVE_OK = 200;
    public static final String PRODUCT_ID = "productId";
    private Product product = new Product();
    private static Cart cart = new Cart();
    private List<Review> lstReview = new ArrayList<>();
    private ActivityProductDetailBinding binding;
    private User user = SharedPrefManager.getInstance(this).getUser();

    private int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.quantity = 0;
        binding.txtQuantity.setText("0");
        binding.btnViewCart.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("productLove") != null) {
            binding.btnRemoveProductLove.setVisibility(View.VISIBLE);
            binding.btnAddProductLove.setVisibility(View.GONE);
        }
        else {
            binding.btnRemoveProductLove.setVisibility(View.GONE);
            binding.btnAddProductLove.setVisibility(View.VISIBLE);
        }
        binding.btnAddReview.setVisibility(View.VISIBLE);
        binding.txtreview.setVisibility(View.GONE);
        binding.txtreview.setVisibility(View.GONE);
        binding.reviewFeature.setVisibility(View.GONE);

        binding.btnAddReview.setOnClickListener(view -> displayAddReview());
        binding.btnCancelReview.setOnClickListener(view -> cancelDisplayAddReview());
        binding.btnSaveReview.setOnClickListener(view -> saveReview());

        binding.btnBackDetail.setOnClickListener(view ->back());
        binding.btnViewCart.setOnClickListener(this::setViewCart);
        binding.plus.setOnClickListener(this::setAddQuantity);
        binding.subtract.setOnClickListener(this::setSubtractQuantity);
        binding.btnAddCart.setOnClickListener(this::setAddCart);
        binding.btnAddProductLove.setOnClickListener(this::setAddProductLove);
        binding.btnRemoveProductLove.setOnClickListener(this::setRemoveProductLove);
        setProduct();

    }
    private void back()
    {
        setResult(GET_PRODUCT_LOVE_OK);
        finish();
    }
    private void setViewCart(View view) {
        Intent intent = new Intent(view.getContext(), CartDetailActivity.class);
        if(this.cart == null)
            view.getContext().startActivity(intent);
        else{
            CartDetailActivity.cart = this.cart;
            view.getContext().startActivity(intent);
        }
        finish();
    }
    private void setAddQuantity(View view) {
        this.quantity += 1;
        binding.txtQuantity.setText(String.valueOf(quantity));
    }
    private void setSubtractQuantity(View view) {
        if (this.quantity <= 0) {
            this.quantity = 0;
            binding.txtQuantity.setText("0");
        } else {
            quantity -= 1;
            binding.txtQuantity.setText(String.valueOf(quantity));
        }
    }
    private void setProduct() {
        Bundle bundle = getIntent().getExtras();
        Log.d("T", bundle.getString(PRODUCT_ID));
        APIService.createService(IProductType.class).getProductById(bundle.getString(PRODUCT_ID)).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product productTemp = new Product(response.body().getId(),response.body().getStoreId(), response.body().getTypeId(), response.body().getName(),
                            response.body().getPrice(), response.body().getImage(), response.body().getDefaultImage(), response.body().getDetail(),
                            response.body().getStar(), response.body().getState());
                    product = productTemp;
                    if (product != null) {
                        setProductImage();
                        setProductTitle();
                        setProductDetail();
                        setProductStore();
                    }
                    getReviews();
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
    private void setProductStore() {

        APIService.createService(IStore.class).getStoreByProductId(this.product.getId())
                .enqueue(new Callback<Store>() {
                    @Override
                    public void onResponse(Call<Store> call, Response<Store> response) {
                        if (response.isSuccessful()) {
                            Store store = response.body();
                            if (store != null) {
                                ((TextView) findViewById(R.id.productStore)).setText(store.getName());
                                ((TextView) findViewById(R.id.productStoreAddress)).setText(store.getAddress());
                            }
                        }
                        else
                            Log.d("T", "Sai dữ liệu");
                    }

                    @Override
                    public void onFailure(Call<Store> call, Throwable t) {
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
    private void setAddCart(View view) {
        if (this.quantity <= 0) {
            Toast.makeText(this, "Số lượng sản phẩm tối thiểu là 1.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cart cartNew = new Cart(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(),this.product.getId(),this.quantity);
        APIService.createService(ICartService.class).addCart(cartNew).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (!response.isSuccessful())
                {
                    Log.d("T", "Đã xảy ra lỗi khi thêm giỏ hàng.");
                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi khi thêm giỏ hàng.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                     cart = response.body();
                    binding.btnAddCart.setVisibility(View.GONE);
                    binding.btnViewCart.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.d("T", "Lỗi hệ thống");
            }
        });

        }
    private void setAddProductLove(View view)
    {
        if (this.quantity <= 0) {
            Toast.makeText(this, "Số lượng sản phẩm tối thiểu là 1.", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("productLove",product.getId());

        APIService.createService(IProductType.class).addProductLove(getIdUser(),map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful())
                {

                    Toast.makeText(ProductDetailActivity.this, "Thêm sản phẩm yêu thích thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Đã thêm vào sản phẩm yêu thích!", Toast.LENGTH_SHORT).show();
                Log.d("UserID",product.getId());
                binding.btnAddProductLove.setVisibility(View.GONE);
                binding.btnRemoveProductLove.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setRemoveProductLove(View view){
        Map<String,String> map = new HashMap<String,String>();
        map.put("productLove",product.getId());
        APIService.createService(IProductType.class).removeProductLove(getIdUser(),map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(ProductDetailActivity.this, "Xóa sản phẩm yêu thích thất bại", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ProductDetailActivity.this, "Xóa sản phẩm yêu thích thành công", Toast.LENGTH_SHORT).show();
                binding.btnAddProductLove.setVisibility(View.VISIBLE);
                binding.btnRemoveProductLove.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setReviews() {
        ReviewAdapter adapter = new ReviewAdapter(getApplicationContext(), lstReview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvProductReview.setLayoutManager(layoutManager);
        binding.rvProductReview.setAdapter(adapter);
    }

    public void getReviews(){
        APIService.createService(IProductType.class).getReviewByProductId(product.getId()).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                lstReview = response.body();
                setReviews();
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu review hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAddReview(){
        binding.btnAddReview.setVisibility(View.GONE);
        binding.txtreview.setText("");
        binding.txtreview.setVisibility(View.VISIBLE);
        binding.txtreview.setVisibility(View.VISIBLE);
        binding.reviewFeature.setVisibility(View.VISIBLE);
    }
    private void cancelDisplayAddReview(){
        binding.btnAddReview.setVisibility(View.VISIBLE);
        binding.txtreview.setVisibility(View.GONE);
        binding.txtreview.setVisibility(View.GONE);
        binding.reviewFeature.setVisibility(View.GONE);
    }
    private void saveReview(){
        String valueReview = binding.txtreview.getText().toString().trim();
        if (valueReview.equals("")) {
            binding.txtreview.setError("Không được để trống!");
            binding.txtreview.requestFocus();
            return;
        }
        Review newReview = new Review(user.getId(), product.getId(), valueReview);
        APIService.createService(IProductType.class).add(newReview).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                cancelDisplayAddReview();
                getReviews();
                Toast.makeText(getApplicationContext(), "Thêm review thành công!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
