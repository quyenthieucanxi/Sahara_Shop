package com.example.saharashop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.adapter.NotificationAdapter;
import com.example.saharashop.adapter.RecyleItemViewAdapter;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.INotification;
import com.example.saharashop.entity.MenuItem;
import com.example.saharashop.entity.Notification;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Notification> lstNotifications = new ArrayList<>();
    private User user = SharedPrefManager.getInstance(getContext()).getUser();

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        getNotification(view);

        return view;
    }

    private void setNotification(@NotNull View view) {
        NotificationAdapter adapter = new NotificationAdapter(lstNotifications);
        RecyclerView rv_account = view.findViewById(R.id.rvNotifications);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_account.setLayoutManager(layoutManager);
        rv_account.setAdapter(adapter);
    }

    public void getNotification(@NotNull View view){
        APIService.createService(INotification.class).getAllNotificationsByUserId(user.getId()).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    return;
                }
                lstNotifications = response.body();
                setNotification(view);
                Toast.makeText(getContext(), "Lấy dữ liệu thông báo thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}