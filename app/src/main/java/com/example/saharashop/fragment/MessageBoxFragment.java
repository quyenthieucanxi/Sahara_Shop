package com.example.saharashop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saharashop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageBoxFragment extends  DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String selectedLanguage;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageBoxFragment() {
        // Required empty public constructor
    }
    public MessageBoxFragment(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageBoxFragment newInstance(String param1, String param2) {
        MessageBoxFragment fragment = new MessageBoxFragment();
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
        View view = inflater.inflate(R.layout.fragment_message_box, container, false);
        view.findViewById(R.id.btnCloseMsg).setOnClickListener(v -> {
            this.dismiss();
        });
        view.findViewById(R.id.btnCancelMsg).setOnClickListener(v -> {
            this.dismiss();
        });
        view.findViewById(R.id.btnOkMsg).setOnClickListener(v -> {
            ChangeLanguage();
            this.dismiss();
        });
        return view;
    }
    private void ChangeLanguage() {
        Toast.makeText(getContext(), this.selectedLanguage, Toast.LENGTH_SHORT).show();
    }
}