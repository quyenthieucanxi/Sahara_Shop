package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.adapter.BillAdapter;
import com.example.saharashop.adapter.NotificationAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.entity.Bill;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.ls.LSSerializer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillHistoryActivity extends AppCompatActivity {
    private List<Bill> lstBill = new ArrayList<>();
    private RecyclerView rv_billHistory;
    private ImageButton btnBack;
    private User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);

        rv_billHistory = findViewById(R.id.rvBillHistory);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getBillHistory();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setBillHistory() {
        BillAdapter adapter = new BillAdapter(lstBill);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_billHistory.setLayoutManager(layoutManager);
        rv_billHistory.setAdapter(adapter);
    }

    public void getBillHistory(){
        APIService.createService(IBillService.class).getAllBillByUserId(user.getId()).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                lstBill = response.body();
                setBillHistory();
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu lịch sử hóa đơn thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}