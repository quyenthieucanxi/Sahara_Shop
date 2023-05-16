package com.example.saharashop.entity;

import android.util.Log;

import com.example.saharashop.R;
import com.example.saharashop.activity.AccountInfoActivity;
import com.example.saharashop.activity.BillCanceledActivity;
import com.example.saharashop.activity.BillHistoryActivity;
import com.example.saharashop.activity.BillReceivedActivity;
import com.example.saharashop.activity.FeatureChangeLanguageActivity;
import com.example.saharashop.activity.FeatureHelpActivity;
<<<<<<< HEAD
import com.example.saharashop.activity.ProductLoveActivity;
=======
import com.example.saharashop.activity.Login;
>>>>>>> ThangLuong
import com.example.saharashop.activity.SettingsActivity;
import com.example.saharashop.fragment.ConfirmOrderFragment;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItem {
    public static final String MENU_ACCOUNT_SETTINGS = "Cài đặt tài khoản";
    public static final String MENU_BILL_HISTORY = "Lịch sử hóa đơn";
    public static final String MENU_LANGUAGE = "Chọn ngôn ngữ";
    public static final String MENU_HELP = "Liên hệ hỗ trợ";
<<<<<<< HEAD
    public static final String MENU_PRODUCT_LOVE = "Sản phầm yêu thích";
=======
    public static final String MENU_CANCEL_BILL = "Hóa đơn đã hủy";
    public static final String MENU_RECEIVED_BILL = "Hóa đơn đã giao thành công";
>>>>>>> ThangLuong
    public static List<String> menuItemTitle;
    public static List<Class> menuItemClass;
    public static List<Integer> menuItemImage;

<<<<<<< HEAD
    static {
        menuItemTitle = Arrays.asList(MENU_ACCOUNT_SETTINGS, MENU_LANGUAGE, MENU_HELP, MENU_BILL_HISTORY, MENU_PRODUCT_LOVE);
        menuItemClass = Arrays.asList(SettingsActivity.class, FeatureChangeLanguageActivity.class, FeatureHelpActivity.class, BillHistoryActivity.class, ProductLoveActivity.class);
=======
    private static void setMenuClassUser(){
        menuItemTitle = Arrays.asList(MENU_ACCOUNT_SETTINGS, MENU_LANGUAGE, MENU_HELP, MENU_BILL_HISTORY);
        menuItemClass = Arrays.asList(SettingsActivity.class, FeatureChangeLanguageActivity.class, FeatureHelpActivity.class, BillHistoryActivity.class);
>>>>>>> ThangLuong
        menuItemImage = Arrays.asList(
                R.drawable.history,
                R.drawable.translation,
                R.drawable.messaging,
                R.drawable.settings,
                R.drawable.product_love
        );
    }

    private static void setMenuClassAdmin(){
        menuItemTitle = Arrays.asList(MENU_ACCOUNT_SETTINGS, MENU_LANGUAGE, MENU_RECEIVED_BILL, MENU_CANCEL_BILL);
        menuItemClass = Arrays.asList(SettingsActivity.class, FeatureChangeLanguageActivity.class, BillReceivedActivity.class, BillCanceledActivity.class);
        menuItemImage = Arrays.asList(
                R.drawable.history,
                R.drawable.translation,
                R.drawable.settings,
                R.drawable.settings
        );
    }

    private Integer discountImageID;
    private String title;
    private Integer bgImageID;

    public MenuItem(String title, Integer discountImageID, Integer bgImageID) {
        this.title = title;
        this.discountImageID = discountImageID;
        this.bgImageID = bgImageID;
    }

    public MenuItem(String title, Integer discountImageID) {
        this(title, discountImageID, null);
    }

    @NotNull
    public static List<MenuItem> createListMenuItem() {
        if(Login.roleID.equals("user"))
        {
            setMenuClassUser();
        }
        else {
            setMenuClassAdmin();
        }
        List<MenuItem> menu = new ArrayList<MenuItem>();
        int num = menuItemClass.size();
        for (int i = 0; i < num; i++) {
            menu.add(new MenuItem(menuItemTitle.get(i), menuItemImage.get(i)));
        }
        return menu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDiscountImageID() {
        return discountImageID;
    }

    public void setDiscountImageID(Integer discountImageID) {
        this.discountImageID = discountImageID;
    }

    public Integer getBgImageID() {
        return bgImageID;
    }

    public void setBgImageID(Integer bgImageID) {
        this.bgImageID = bgImageID;
    }

    public static Class getLayout(Integer position) {
        return menuItemClass.get(position);
    }

    public static Class getLayout(String title) {
        int position = menuItemTitle.indexOf(title);
        return menuItemClass.get(position);
    }
}
