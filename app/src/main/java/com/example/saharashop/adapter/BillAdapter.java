package com.example.saharashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saharashop.R;
import com.example.saharashop.activity.CartDetailActivity;
import com.example.saharashop.activity.MainAdminActivity;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.fragment.NotificationFragment;
import com.example.saharashop.untils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<Bill> lstBill;

    public BillAdapter(List<Bill> lstBill) {
        this.lstBill = lstBill;
    }

    private AdapterCallback mAdapterCallback;

    public BillAdapter(List<Bill> lstBill, AdapterCallback adapterCallback) {
        this.lstBill = lstBill;
        mAdapterCallback = adapterCallback;
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
        holder.billQuantity.setText(String.valueOf(bill.getQuantity()));
        holder.billDeliveryAddress.setText(bill.getAddress());
        holder.idBill = bill.getId();
        if(bill.getStatus().equals("Uncheck"))
            holder.billStatus.setText("Chưa xác nhận");
        else
            holder.billStatus.setText("Đã xác nhận");
        holder.billTime.setText(String.valueOf(bill.getDate()));
    }

    @Override
    public int getItemCount() {
        return lstBill == null ? 0 : lstBill.size();
    }

    private void setStatusBill(View view, String idBill){
        Map<String, String> status = new HashMap<String, String>();
        status.put("status", "Checked");
        APIService.createService(IBillService.class).update(idBill, status).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MainAdminActivity.SELECTED.equals("menuHome_admin"))
                    mAdapterCallback.onMethodCallback();

            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {

            }
        });
    }

    private void setConfirm(View view, String idBill) {
        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Xác nhận")
                .setMessage("Bạn có muốn xác nhận đơn hàng không?")
                .setPositiveButton("Có", (dialogInterface, i) -> {

                    setStatusBill(view, idBill);
                    Toast.makeText(view.getContext(), "Đã xác nhận thành công", Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("Không", null)
                .setIcon(R.drawable.shutdown)
                .show();
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView billProductName, productPrice,
                billTotalPrice, billQuantity,
                billDeliveryAddress, billTime, billStatus;
        public String idBill;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            billProductName = itemView.findViewById(R.id.billProductName);
            productPrice = itemView.findViewById(R.id.productPrice);
            billTotalPrice = itemView.findViewById(R.id.billTotalPrice);
            billQuantity = itemView.findViewById(R.id.billQuantity);
            billDeliveryAddress = itemView.findViewById(R.id.billDeliveryAddress);
            billTime = itemView.findViewById(R.id.billTime);
            billStatus = itemView.findViewById(R.id.billStatus);

            if(MainAdminActivity.SELECTED.equals("menuHome_admin"))
            {
                itemView.setOnClickListener(v -> {
                    setConfirm(itemView, idBill);
                });
            }

        }

    }


}
