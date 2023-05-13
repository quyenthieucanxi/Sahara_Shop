package com.example.saharashop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.activity.ProductDetailActivity;
import com.example.saharashop.adapter.ProductAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IDiscount;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.databinding.FragmentCartBinding;
import com.example.saharashop.databinding.FragmentDiscountBinding;
import com.example.saharashop.entity.Product;
import com.example.saharashop.entity.Promo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private List<Product> products = new ArrayList<>();
    private FragmentDiscountBinding binding;
    List<Product> productList = new ArrayList<>();
    public DiscountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscountFragment newInstance(String param1, String param2) {
        DiscountFragment fragment = new DiscountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscountBinding.inflate(inflater, container, false);
        this.view = binding.getRoot();
        // Inflate the layout for this fragment
        getPromoProducts("2");
        binding.layoutSearchDiscount.setVisibility(View.GONE);
        getSearchResult();
        return view;
    }

    private  void getPromoProducts(String limit)
    {

        APIService.createService(IProductType.class).getTopPromos(limit).enqueue(new Callback<List<Promo>>() {
            @Override
            public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {
                if (!response.isSuccessful())
                {
                    Log.d("Q","Thất bại");
                }
                else {
                    Log.d("Q","Ok");
                    ArrayList<String> promoId = new ArrayList<>();
                    List<Promo> promos = response.body();
                    for (int i=0;i< promos.size();i++)
                    {
                        promoId.add(promos.get(i).getProductId());
                    }

                    for (int j =0 ; j< promoId.size();j++){
                        getProductById(promoId.get(j));
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Promo>> call, Throwable t) {
                Log.d("Q","Lỗi ");
            }
        });
    }
    private  void getProductById(String id)
    {
        APIService.createService(IProductType.class).getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product productTemp = new Product(response.body().getId(),response.body().getStoreId(), response.body().getTypeId(), response.body().getName(),
                            response.body().getPrice(), response.body().getImage(), response.body().getDefaultImage(), response.body().getDetail(),
                            response.body().getStar(), response.body().getState());
                    Log.d("Q",productTemp.getName());
                     productList.add(productTemp);
                    ProductAdapter gv_adapter = new ProductAdapter(getContext(), productList);
                    binding.gvSponsor.setAdapter(gv_adapter);
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
    private  void getDiscountProductByName(String txtSearch)
    {
        APIService.createService(IDiscount.class).getDiscountProductByName(txtSearch).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Tìm kiếm thất bại", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    products= response.body();
                    if (products != null) {
                        setSearchResult(products);
                        binding.layoutSponsor.setVisibility(View.GONE);
                    } else
                        binding.layoutSearchDiscount.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void getSearchResult() {
        binding.txtSearchDiscount.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //ArrayList<Product> products = productDbHelper.getDiscountProductByName(binding.txtSearchDiscount.getQuery().toString());
                getDiscountProductByName(binding.txtSearchDiscount.getQuery().toString());

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void setSearchResult(@NotNull List<Product> products) {
        binding.layoutSearchDiscount.setVisibility(View.VISIBLE);
        binding.tvNumDiscount.setText(String.valueOf(products.size()));
        ProductAdapter adapter = new ProductAdapter(getContext(), products);
        binding.gvSearchResult.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            intent.putExtra(ProductDetailActivity.PRODUCT_ID, adapter.getItemId(position));
            startActivity(intent);
        });
        binding.gvSearchResult.setAdapter(adapter);
    }
}










