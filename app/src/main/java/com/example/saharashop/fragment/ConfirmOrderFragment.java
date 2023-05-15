package com.example.saharashop.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.activity.MainAdminActivity;
import com.example.saharashop.activity.ProductDetailActivity;
import com.example.saharashop.adapter.BillAdapter;
import com.example.saharashop.adapter.NotificationAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IBillService;
import com.example.saharashop.databinding.FragmentConfirmOrderBinding;
import com.example.saharashop.databinding.FragmentDiscountBinding;
import com.example.saharashop.entity.Bill;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmOrderFragment extends Fragment implements BillAdapter.AdapterCallback{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Bill> lstBill = new ArrayList<>();
    private FragmentConfirmOrderBinding binding;
    private View view;
    private String status;

    public ConfirmOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConfirmOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmOrderFragment newInstance(String param1, String param2) {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
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
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false);
        this.view = binding.getRoot();
        if(MainAdminActivity.SELECTED.equals("menuHome_admin")) {
            status = "Uncheck";
            getConfirmOrder(status);
        }
        else {
            status = "Checked";
            getConfirmOrder(status);
        }
        return view;
    }

    @Override
    public void onMethodCallback() {
        getConfirmOrder(status);
    }

    @Override
    public void onResume() {
        super.onResume();
        getConfirmOrder(status);
    }

    private void setConfirmOrder() {
        BillAdapter adapter = new BillAdapter(lstBill, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvConfirmOrder.setLayoutManager(layoutManager);
        binding.rvConfirmOrder.setAdapter(adapter);
    }

    public void getConfirmOrder(String status){
        APIService.createService(IBillService.class).getAllBill(status).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                lstBill = response.body();
                setConfirmOrder();
                view.findViewById(R.id.noMoreBills).setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lấy dữ liệu lịch sử hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}