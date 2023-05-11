package com.example.saharashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.saharashop.R;
import com.example.saharashop.activity.CartDetailActivity;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IProductType;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.entity.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public List<Cart> carts;
    private Context context;
    public CartAdapter(Context context,List<Cart> carts) {
        this.carts = carts;
        this.context = context;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        APIService.createService(IProductType.class).getProductById(cart.getProductId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Glide.with(context).load(response.body().getImage()).into(holder.cartImage);
                    holder.cartTitle.setText(response.body().getName());
                    holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));
                    String status = cart.getStatus() == "Paid" ? "Đã thanh toán" : "Chưa thanh toán";
                    holder.cartStatus.setText(status);
                }
                else {
                    Log.d("Error","Lỗi ");
                }
            }

        @Override
        public void onFailure(Call<Product> call, Throwable t) {
            Log.d("Error","Lỗi hệ thống");
        }
    });
        holder.cart = cart;
    }

    @Override
    public int getItemCount() {
        return carts == null ? 0 : carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImage;
        TextView cartTitle;
        TextView cartQuantity;
        TextView cartStatus;
        Cart cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cartImage);
            cartTitle = itemView.findViewById(R.id.cartProductName);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartStatus = itemView.findViewById(R.id.cartStatus);

            itemView.setOnClickListener(v -> {
                CartDetailActivity.cart = cart;
                Intent intent = new Intent(itemView.getContext(), CartDetailActivity.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
