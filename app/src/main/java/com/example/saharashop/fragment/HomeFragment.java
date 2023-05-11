package com.example.saharashop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.activity.MainActivity;
import com.example.saharashop.activity.SearchActivity;
import com.example.saharashop.adapter.ProductTypeAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.entity.ProductType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static final String PROMO_PRODUCT_ID = "productId";
    public static final String PRODUCT_TYPE_ID = "productType";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<ProductType> productTypes = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SearchView searchView = view.findViewById(R.id.tvSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("search", searchView.getQuery().toString());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getAllProductTypes();

        return view;
    }

    private void setProductItem(View view){

        ProductTypeAdapter productTypeAdapter = new ProductTypeAdapter(getContext(), productTypes);
        GridView gv_product = view.findViewById(R.id.homeProduct);
        gv_product.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this.getContext(), SearchActivity.class);
            intent.putExtra(PRODUCT_TYPE_ID, productTypeAdapter.getItemId_v2(position));
            startActivity(intent);
        });
        gv_product.setAdapter(productTypeAdapter);
        Log.d("T", "Value: " + productTypes.size());
    }

    private void getAllProductTypes(){
        APIService.createService(IProductType.class).getAllProductTypes().enqueue(new Callback<List<ProductType>>() {
            @Override
            public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                if(response.code() != 200){
                    return;
                }

                productTypes = response.body();
                setProductItem(getView());
                Log.d("T09", "Value: " + productTypes.size());

            }

            @Override
            public void onFailure(Call<List<ProductType>> call, Throwable t) {

            }


        });
    }

}