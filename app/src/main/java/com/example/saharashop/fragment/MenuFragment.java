package com.example.saharashop.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saharashop.R;
import com.example.saharashop.activity.Login;
import com.example.saharashop.adapter.RecyleItemViewAdapter;
import com.example.saharashop.databinding.ActivitySearchBinding;
import com.example.saharashop.databinding.FragmentMenuBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.MenuItem;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv_menuFullName;
    private TextView tv_menuUsername;

    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        //ButterKnife.bind(this, view);

        tv_menuUsername = view.findViewById(R.id.menuUsername);
        tv_menuFullName = view.findViewById(R.id.menuFullName);

        setMenuItemSection(view);
        setLoggedInUserInfo();
        //checkLogin();

        return view;
    }
    private void setLoggedInUserInfo() {
        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(getContext()).getUser();
            Account1 account1 = SharedPrefManager.getInstance(getContext()).getAccount();
            //binding.menuAvatar.setImageBitmap(user.getAvatar());
            tv_menuFullName.setText(user.getFullname());
            tv_menuUsername.setText("@" + account1.getUsername());
        }
    }
    private void setMenuItemSection(@NotNull View view) {
        List<MenuItem> lstMenuItems = MenuItem.createListMenuItem();
        RecyleItemViewAdapter adapter = new RecyleItemViewAdapter(lstMenuItems);
        RecyclerView rv_account = view.findViewById(R.id.rv_menu_item);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_account.setLayoutManager(layoutManager);
        rv_account.setAdapter(adapter);
    }
}