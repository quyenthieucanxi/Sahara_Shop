package com.example.saharashop.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.adapter.CartAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.ICartService;
import com.example.saharashop.databinding.FragmentCartBinding;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.untils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    List<Cart> unpaidCarts = new ArrayList<>();
    private FragmentCartBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        binding = FragmentCartBinding.inflate(inflater, container, false);
        this.view = binding.getRoot();
        getUnpaidCart(view);
        return this.view;
    }
    private void getCartByUserId(){

        APIService.createService(ICartService.class).getCartByUserId(SharedPrefManager.getInstance(getContext()).getUser().getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Đã xảy ra lỗi khi load giỏ hàng.", Toast.LENGTH_SHORT).show();
                }
                else  {
                    unpaidCarts  = response.body();
                   /* List<Map<String, String>> _unpaidCarts = new ArrayList<>();
                    for (int i=0;i<unpaidCarts.size();i++)
                    {
                        _unpaidCarts.add(Map.of("productID",unpaidCarts.get(i).getProductId(),"quantity",String.valueOf(unpaidCarts.get(i).getQuantity())));

                    }
                    List<Map<String, String>> newUnpaidCarts= _unpaidCarts.stream()
                            .collect(Collectors.groupingBy(
                                    cart -> cart.get("productID"),
                                    Collectors.summingInt(cart -> Integer.parseInt(cart.get("quantity")))))
                            .entrySet().stream()
                            .map(entry -> Map.of("productID", entry.getKey(), "quantity", Integer.toString(entry.getValue())))
                            .collect(Collectors.toList());
                    for (int i=0;i<unpaidCarts.size();i++) {
                        if (unpaidCarts.get(i).getProductId() == newUnpaidCarts.get(i).get("productID"))
                        {
                            unpaidCarts.get(i).setQuantity(Integer.parseInt(newUnpaidCarts.get(i).get("productID")));
                        }
                    }*/

                    loadCart();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });
    }
    private  void loadCart()
    {
        if (unpaidCarts.size() > 0) {
            CartAdapter adapter = new CartAdapter(getContext(),unpaidCarts);
            RecyclerView rvCart = view.findViewById(R.id.rvCart);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvCart.setLayoutManager(layoutManager);
            rvCart.setAdapter(adapter);
            binding.noMoreCarts.setVisibility(View.GONE);
            binding.cartList.setVisibility(View.VISIBLE);
            rvCart.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {

                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (adapter.getItemCount() == 0) {
                        binding.noMoreCarts.setVisibility(View.VISIBLE);
                        binding.cartList.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            binding.noMoreCarts.setVisibility(View.VISIBLE);
            binding.cartList.setVisibility(View.GONE);
        }
    }
    private void getUnpaidCart(View view) {

        getCartByUserId();

    }
}