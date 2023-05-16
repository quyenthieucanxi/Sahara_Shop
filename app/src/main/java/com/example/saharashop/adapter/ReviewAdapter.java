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
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Cart;
import com.example.saharashop.entity.Product;
import com.example.saharashop.entity.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    public List<Review> reviews;
    private Context context;
    public ReviewAdapter(Context context,List<Review> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.txtfullname.setText(review.getFullname());
        holder.txtcontent.setText(review.getContent());
        holder.txttime.setText(review.getTime());
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtfullname, txtcontent, txttime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtfullname = itemView.findViewById(R.id.txtfullname);
            txtcontent = itemView.findViewById(R.id.txtcontent);
            txttime = itemView.findViewById(R.id.txttime);

        }
    }
}
