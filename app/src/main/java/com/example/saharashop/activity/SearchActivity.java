package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.adapter.ProductAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.databinding.ActivitySearchBinding;
import com.example.saharashop.databinding.LoginBinding;
import com.example.saharashop.entity.Product;
import com.example.saharashop.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private ArrayList<String> selectedProductTypes = new ArrayList<>();
    private String txtSearch;
    private List<Product> products = new ArrayList<>();
    private List<Product> ListproductsBySearch = new ArrayList<>();
    private List<Product> productsByListTypeId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.chipClothes.setOnCheckedChangeListener(this::setChipClothesOnCheckedChanged);
        binding.chipFood.setOnCheckedChangeListener(this::setChipFoodOnCheckedChanged);
        binding.chipFreshFood.setOnCheckedChangeListener(this::setChipFreshFoodOnCheckedChanged);
        binding.chipFruit.setOnCheckedChangeListener(this::setChipFruitOnCheckedChanged);
        binding.chipDrink.setOnCheckedChangeListener(this::setChipDrinkOnCheckedChanged);
        binding.chipElectronic.setOnCheckedChangeListener(this::setChipElectronicOnCheckedChanged);
        binding.chipOthers.setOnCheckedChangeListener(this::setChipOthersOnCheckedChanged);
        binding.chipAllProductTypes.setOnCheckedChangeListener(this::setChipAllTypesOnCheckedChanged);
        //binding.chipAllProductTypes.setChecked(true);

        this.txtSearch = getIntent().getStringExtra("search");
        String productTypeId = getIntent().getStringExtra(HomeFragment.PRODUCT_TYPE_ID);

        Log.d("T", "Value: -----" + this.txtSearch);

        if (this.txtSearch != null) {
            binding.tvSearch.setText(txtSearch);
            getSearchResult();
        } else {
            binding.tvSearch.setText("Xem sản phẩm theo loại");
            setShowProductByTypeId(productTypeId);
        }

    }

    private void setShowProductByTypeId(String productTypeId) {
        if (productTypeId != null) {
            switch (productTypeId) {
                case "645a689ebb7d9f27fb9a75d8":
                    binding.chipClothes.setChecked(true);
                    break;
                case "645a68e4bb7d9f27fb9a75d9":
                    binding.chipFood.setChecked(true);
                    break;
                case "645a68ffbb7d9f27fb9a75da":
                    binding.chipFruit.setChecked(true);
                    break;
                case "645a6919bb7d9f27fb9a75db":
                    binding.chipDrink.setChecked(true);
                    break;
                case "645a6936bb7d9f27fb9a75dc":
                    binding.chipElectronic.setChecked(true);
                    break;
                case "645a69a4bb7d9f27fb9a75dd":
                    binding.chipFreshFood.setChecked(true);
                case "645a69c0bb7d9f27fb9a75de":
                    binding.chipOthers.setChecked(true);
                default:
                    break;
            }
            binding.chipAllProductTypes.setChecked(false);
            getProductByTypeId(productTypeId);

        }
    }

    private void getProductByTypeId(String productTypeId){
        APIService.createService(IProductType.class).getProductByTypeId(productTypeId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.d("TYPEID", "Lỗi!!!!!!!!");
                }
                products = response.body();
                setProductsOnGridView(products);

                Log.d("T", "Value products: " + products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("TYPEID", "Lỗi!!!!!!!!");
            }
        });
    }

    private void setChipClothesOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a689ebb7d9f27fb9a75d8");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a689ebb7d9f27fb9a75d8");
        getSearchResultByType();
    }

    private void setChipFoodOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a68e4bb7d9f27fb9a75d9");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a68e4bb7d9f27fb9a75d9");
        getSearchResultByType();
    }

    private void setChipFruitOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a68ffbb7d9f27fb9a75da");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a68ffbb7d9f27fb9a75da");
        getSearchResultByType();
    }

    private void setChipDrinkOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a6919bb7d9f27fb9a75db");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a6919bb7d9f27fb9a75db");
        getSearchResultByType();
    }

    private void setChipElectronicOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a6936bb7d9f27fb9a75dc");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a6936bb7d9f27fb9a75dc");
        getSearchResultByType();
    }

    private void setChipFreshFoodOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a69a4bb7d9f27fb9a75dd");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a69a4bb7d9f27fb9a75dd");
        getSearchResultByType();
    }

    private void setChipOthersOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("645a69c0bb7d9f27fb9a75de");
            binding.chipAllProductTypes.setChecked(false);
        } else
            selectedProductTypes.remove("645a69c0bb7d9f27fb9a75de");
        getSearchResultByType();
    }

    private void setChipAllTypesOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (this.txtSearch == null  ) {
            if (b)
                getAllProduct();
            else
                getSearchResultByType();
            return;
        }
        if (b) {
            binding.chipFood.setChecked(false);
            binding.chipClothes.setChecked(false);
            binding.chipDrink.setChecked(false);
            binding.chipFreshFood.setChecked(false);
            binding.chipFruit.setChecked(false);
            binding.chipElectronic.setChecked(false);
            binding.chipOthers.setChecked(false);
            selectedProductTypes.clear();

            selectedProductTypes.add("645a689ebb7d9f27fb9a75d8");
            selectedProductTypes.add("645a68e4bb7d9f27fb9a75d9");
            selectedProductTypes.add("645a68ffbb7d9f27fb9a75da");
            //getSearchResult();
            getSearchResultByType();
        } else {
            selectedProductTypes.clear();
        }
//        ProductDbHelper productDbHelper = new ProductDbHelper(this);
//        ArrayList<Product> products = productDbHelper.getFullSearchResult(txtSearch);
        getSearchResult();

    }
    private  void getAllProduct(){

        APIService.createService(IProductType.class).getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.d("T", "Thất bại!");
                    return;
                }
                products = response.body();
                setProductsOnGridView(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void getSearchResultByType() {
        Map<String, ArrayList<String>> requestBody = new HashMap<>();

        requestBody.put("listTypeId", selectedProductTypes);

        APIService.createService(IProductType.class).getProductByListTypeId(requestBody).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.d("T", "Thất bại!");
                    return;
                }
                productsByListTypeId = response.body();
                setProductsOnGridView(productsByListTypeId);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("T", "Thất bại!");
            }
        });
    }
    private void getSearchResult() {
        if (this.txtSearch == null)
            return;
        Map<String, String> txtSearchRq = new HashMap<>();
        txtSearchRq.put("typeName", txtSearch);
        APIService.createService(IProductType.class).getProductByTypeName(txtSearchRq).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.d("T", "Thất bại!");
                    return;
                }
                ListproductsBySearch = response.body();
                if (products == null)
                    return;
                setProductsOnGridView(ListproductsBySearch);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("T", "Thất bại!");
            }
        });

    }


    private void setProductsOnGridView(List<Product> products) {
        TextView tvNumberProducts = findViewById(R.id.tvNumProducts);
        tvNumberProducts.setText(String.valueOf(products.size()));
        ProductAdapter adapter = new ProductAdapter(this, products);

        GridView gridView = findViewById(R.id.searchResult);
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
           intent.putExtra(ProductDetailActivity.PRODUCT_ID, adapter.getItemId_v2(position));
          startActivity(intent);
       });
        gridView.setAdapter(adapter);
    }
}