package com.example.saharashop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import com.example.saharashop.R;
import com.example.saharashop.activity.Login;
import com.example.saharashop.activity.MainActivity;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.databinding.FragmentMenuBinding;
import com.example.saharashop.entity.MenuItem;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.fragment.NotificationFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> lstNotification;
    private AdapterCallback mAdapterCallback;

    public NotificationAdapter(List<Notification> lstNotification, AdapterCallback adapterCallback) {
        this.lstNotification = lstNotification;
        mAdapterCallback = adapterCallback;
    }

    public List<Notification> getLstNotification() {
        return lstNotification;
    }

    public void setLstNotification(ArrayList<Notification> lstNotification) {
        this.lstNotification = lstNotification;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notify_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = lstNotification.get(position);
        holder.notify_detail.setText(notification.getMessage());
        holder.id = notification.getId();
    }


    @Override
    public int getItemCount() {
        return lstNotification == null ? 0 : lstNotification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView notify_detail;
        public String id;
        public ImageView dismissNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notify_detail = itemView.findViewById(R.id.notify_detail);
            dismissNotification = itemView.findViewById(R.id.dismissNotification);

            dismissNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleDismiss(id, v);

                }
            });
        }
    }

    private void handleDismiss(String id, View view) {
        APIService.createService(INotification.class).update(id).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAdapterCallback.onMethodCallback();
                Toast.makeText(view.getContext(), "Thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Toast.makeText(view.getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface AdapterCallback {
        void onMethodCallback();
    }

}
