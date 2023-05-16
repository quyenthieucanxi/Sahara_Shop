package com.example.saharashop.activity;

import static com.example.saharashop.activity.ProductDetailActivity.GET_PRODUCT_LOVE;
import static com.example.saharashop.activity.ProductDetailActivity.GET_PRODUCT_LOVE_OK;
import static com.example.saharashop.untils.SharedPrefManager.getIdUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.adapter.BillAdapter;
import com.example.saharashop.adapter.ProductAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.databinding.ActivityProductLoveBinding;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductLoveActivity extends AppCompatActivity {
    private ActivityProductLoveBinding binding;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductLoveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(view -> finish());
        getProductLove();
    }
    private void setProductsOnGridView(List<Product> products) {
        binding.tvNumProducts.setText(String.valueOf(products.size()));
        ProductAdapter adapter = new ProductAdapter(this, products);

        GridView gridView = binding.searchResult;
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("productLove","yes");
            intent.putExtra(ProductDetailActivity.PRODUCT_ID, adapter.getItemId_v2(position));
            startActivityForResult(intent,GET_PRODUCT_LOVE);
        });
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_PRODUCT_LOVE && resultCode == GET_PRODUCT_LOVE_OK) {
            // Xử lý kết quả trả về ở đây
            getProductLove();
        }
    }

    public void getProductLove(){
        APIService.createService(IProductType.class).getProductLoveByUserId(getIdUser()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Thất lại lấy sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                products = response.body();
                setProductsOnGridView(products);
                binding.noMoreBills.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Lấy dữ sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}