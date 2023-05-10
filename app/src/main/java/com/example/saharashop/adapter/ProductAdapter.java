package com.example.saharashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saharashop.R;
import com.example.saharashop.entity.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> productList) {
        inflater = LayoutInflater.from(context);
        this.productList = productList;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductView productView;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_product_item, parent, false);

            productView = new ProductView();
            productView.ibtnProduct = convertView.findViewById(R.id.ibtnProduct);
            productView.tv_product_name = convertView.findViewById(R.id.tv_product_name);
            productView.tvDiscount = convertView.findViewById(R.id.tvDiscount);

            convertView.setTag(productView);
        } else {
            productView = (ProductView) convertView.getTag();
        }


        Product product = productList.get(position);

        productView.ibtnProduct.setImageBitmap(product.getImage());
        productView.tv_product_name.setText(product.getName());

        if (null != product.getDiscount()) {
            productView.tvDiscount.setVisibility(View.VISIBLE);
            Float discountValue = product.getDiscount().getValue();
            productView.tvDiscount.setText(discountValue.toString());
        } else {
            productView.tvDiscount.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private class ProductView {
        private ImageView ibtnProduct;
        private TextView tv_product_name;
        private TextView tvDiscount;
    }
}
