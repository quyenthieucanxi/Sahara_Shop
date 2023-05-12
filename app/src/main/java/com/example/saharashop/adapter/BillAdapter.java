package com.example.saharashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.fragment.NotificationFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<Bill> lstBill;

    public BillAdapter(List<Bill> lstBill) {
        this.lstBill = lstBill;
    }

    public List<Bill> getLstBill() {
        return lstBill;
    }

    public void setLstBill(List<Bill> lstBill) {
        this.lstBill = lstBill;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bill_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = getLstBill().get(position);
        holder.billProductName.setText(bill.getProductName());
        holder.productPrice.setText(String.valueOf(bill.getPrice()));
        holder.billTotalPrice.setText(String.valueOf(bill.getQuantity()* bill.getPrice()));
        holder.billQuantity.setText(String.valueOf(bill.getPrice()));
        holder.billDeliveryAddress.setText(bill.getAddress());
        holder.billTime.setText(String.valueOf(bill.getDate()));
    }

    @Override
    public int getItemCount() {
        return lstBill == null ? 0 : lstBill.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView billProductName, productPrice, billTotalPrice, billQuantity,billDeliveryAddress, billTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            billProductName = itemView.findViewById(R.id.billProductName);
            productPrice = itemView.findViewById(R.id.productPrice);
            billTotalPrice = itemView.findViewById(R.id.billTotalPrice);
            billQuantity = itemView.findViewById(R.id.billQuantity);
            billDeliveryAddress = itemView.findViewById(R.id.billDeliveryAddress);
            billTime = itemView.findViewById(R.id.billTime);

        }

    }


}
