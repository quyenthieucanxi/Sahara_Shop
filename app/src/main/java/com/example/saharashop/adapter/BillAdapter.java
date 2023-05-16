package com.example.saharashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saharashop.R;
import com.example.saharashop.activity.BillHistoryActivity;
import com.example.saharashop.activity.CartDetailActivity;
import com.example.saharashop.activity.Login;
import com.example.saharashop.activity.MainAdminActivity;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.entity.User;
import com.example.saharashop.fragment.NotificationFragment;
import com.example.saharashop.untils.SharedPrefManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private List<Bill> lstBill;

    private String roleID = Login.roleID;

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
        Bill bill = lstBill.get(position);
        holder.billProductName.setText(bill.getProductName());
        holder.productPrice.setText(String.valueOf(bill.getPrice()));
        holder.billTotalPrice.setText(String.valueOf(bill.getQuantity()* bill.getPrice()));
        holder.billQuantity.setText(String.valueOf(bill.getQuantity()));
        holder.billDeliveryAddress.setText(bill.getAddress());
        holder.idBill = bill.getId();
        holder.idUser = bill.getUserId();
        holder.productName = bill.getProductName();
        holder.quantity = String.valueOf(bill.getQuantity());
        if(bill.getStatus().equals("UncheckOrder"))
            holder.billStatus.setText("Chưa xác nhận đơn hàng");
        else if(bill.getStatus().equals("UncheckTransport"))
            holder.billStatus.setText("Chưa xác nhận vận chuyển");
        else if(bill.getStatus().equals("UncheckDelivery"))
            holder.billStatus.setText("Chưa xác nhận giao hàng");
        else if(bill.getStatus().equals("UncheckReceived"))
            holder.billStatus.setText("Chưa xác nhận nhận hàng");
        else if(bill.getStatus().equals("Deleted"))
            holder.billStatus.setText("Đơn hàng bị hủy");
        else
            holder.billStatus.setText("Đã nhận hàng thành công");
        holder.billTime.setText(String.valueOf(bill.getDate()));
    }

    @Override
    public int getItemCount() {
        return lstBill == null ? 0 : lstBill.size();
    }

    private void setStatusBill(View view, String idBill, String idUser, String productName, String quantity, String status){
        Map<String, String> valueStatus = new HashMap<String, String>();
        valueStatus.put("status", status);
        APIService.createService(IBillService.class).update(idBill, valueStatus).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                //private void addNotification(String idBill, String idUser, String productName, String quantity, String status){
                addNotification(idBill, idUser, productName, quantity, status);
                mAdapterCallback.onMethodCallback();

            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {

            }
        });
    }

    private void setConfirm(View view, String idBill, String idUser, String productName, String quantity, String status) {
        String text = "";
        switch (status){
            case "Deleted":
                text = "Bạn có chắc chắn muốn hủy đơn hàng không?";
                break;
            case "UncheckTransport":
                text = "Bạn có muốn xác nhận đơn hàng không?";
                break;
            case "UncheckDelivery":
                text = "Bạn có muốn xác nhận vận chuyển đơn hàng không?";
                break;
            case "UncheckReceived":
                text = "Bạn có muốn xác nhận giao đơn hàng không?";
                break;
            case "Checked":
                text = "Bạn có muốn xác nhận đã giao hàng thành công không?";
                break;
            default:
                break;
        }
        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Xác nhận")
                .setMessage(text)
                .setPositiveButton("Có", (dialogInterface, i) -> {

                    setStatusBill(view, idBill, idUser, productName, quantity, status);
                    Toast.makeText(view.getContext(), "Đã xác nhận thành công", Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton("Không", null)
                .setIcon(R.drawable.shutdown)
                .show();
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

    private void addNotification(String idBill, String idUser, String productName, String quantity, String status){
        String text = "";
        Date currentDate = new Date();
        switch (status){
            case "UncheckTransport":
                text = "được xác nhận";
                break;
            case "UncheckDelivery":
                text = "được vận chuyển";
                break;
            case "UncheckReceived":
                text = "được giao hàng";
                break;
            case "Checked":
                text = "được giao thành công!";
                break;
            case "Deleted":
                text = "bị hủy!";
                break;
            default:
                break;
        }
        Notification notification = new Notification(idUser, "Đơn hàng mã "+idBill+" gồm: "+quantity+" "+productName+" đã "+text+" lúc "+ currentDate, true);
        APIService.createService(INotification.class).add(notification).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(!response.isSuccessful()){
                    return;
                }
            }
            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView billProductName, productPrice,
                billTotalPrice, billQuantity,
                billDeliveryAddress, billTime, billStatus, txtCancelBill;
        public LinearLayout billFeature;

        public ImageView billCancleImage, billOkImage;
        public String idBill, idUser, productName, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            billProductName = itemView.findViewById(R.id.billProductName);
            productPrice = itemView.findViewById(R.id.productPrice);
            billTotalPrice = itemView.findViewById(R.id.billTotalPrice);
            billQuantity = itemView.findViewById(R.id.billQuantity);
            billDeliveryAddress = itemView.findViewById(R.id.billDeliveryAddress);
            billTime = itemView.findViewById(R.id.billTime);
            billStatus = itemView.findViewById(R.id.billStatus);
            txtCancelBill = itemView.findViewById(R.id.txtCancelBill);

            billCancleImage = itemView.findViewById(R.id.billCancleImage);
            billOkImage = itemView.findViewById(R.id.billOkImage);

            billFeature = itemView.findViewById(R.id.billFeature);
            billFeature.setVisibility(View.GONE);


            if(MainAdminActivity.SELECTED.equals("menuOrder_admin") && roleID.equals("admin"))
            {
                billFeature.setVisibility(View.VISIBLE);

                billCancleImage.setOnClickListener(v -> {
                    setConfirm(itemView, idBill, idUser, productName, quantity, "Deleted");
                });

                billOkImage.setOnClickListener(v -> {
                    setConfirm(itemView, idBill, idUser, productName, quantity, "UncheckTransport");
                });
            }
            else if(MainAdminActivity.SELECTED.equals("menuTransport_admin") && roleID.equals("admin"))
            {
                billFeature.setVisibility(View.VISIBLE);
                billCancleImage.setVisibility(View.GONE);
                txtCancelBill.setVisibility(View.GONE);
                billOkImage.setVisibility(View.VISIBLE);

                billOkImage.setOnClickListener(v -> {
                    setConfirm(itemView, idBill, idUser, productName, quantity, "UncheckDelivery");
                });
            }
            else if(MainAdminActivity.SELECTED.equals("menuDelivery_admin") && roleID.equals("admin"))
            {
                billFeature.setVisibility(View.VISIBLE);
                billCancleImage.setVisibility(View.GONE);
                txtCancelBill.setVisibility(View.GONE);
                billOkImage.setVisibility(View.VISIBLE);

                billOkImage.setOnClickListener(v -> {
                    setConfirm(itemView, idBill, idUser, productName, quantity, "UncheckReceived");
                });
            }
            else if(MainAdminActivity.SELECTED.equals("menuReceived_admin") && roleID.equals("admin"))
            {
                billFeature.setVisibility(View.VISIBLE);
                billCancleImage.setVisibility(View.GONE);
                txtCancelBill.setVisibility(View.GONE);
                billOkImage.setVisibility(View.VISIBLE);

                billOkImage.setOnClickListener(v -> {
                    setConfirm(itemView, idBill, idUser, productName, quantity, "Checked");
                });
            }


        }

    }


}
