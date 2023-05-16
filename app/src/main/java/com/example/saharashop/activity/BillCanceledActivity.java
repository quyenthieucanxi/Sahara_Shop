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
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.databinding.ActivityBillCanceledBinding;
import com.example.saharashop.entity.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillCanceledActivity extends AppCompatActivity {

    private List<Bill> lstBill = new ArrayList<>();
    private RecyclerView rvBillCanceled;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_canceled);


        rvBillCanceled = findViewById(R.id.rvBillCanceled);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(view -> finish());
        getBillCanceled();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setBillCanceled() {
        BillAdapter adapter = new BillAdapter(lstBill);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvBillCanceled.setLayoutManager(layoutManager);
        rvBillCanceled.setAdapter(adapter);
    }

    public void getBillCanceled(){
        APIService.createService(IBillService.class).getAllBill("Deleted").enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                lstBill = response.body();
                setBillCanceled();
                findViewById(R.id.noMoreBills).setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Lấy dữ hóa đơn bị hủy thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}