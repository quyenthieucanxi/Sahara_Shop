package com.example.saharashop.api;

import com.example.saharashop.entity.Product;
import com.example.saharashop.entity.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IProductType {
    @GET("productType/getAllProductTypes")
    Call<List<ProductType>> getAllProductTypes();

    @GET("product/getProductByTypeId/{typeId}")
    Call<List<Product>> getProductByTypeId(@Path("typeId") String typeId);

    @POST("product/getProductByListTypeId")
    Call<List<Product>> getProductByListTypeId(@Body Map<String, ArrayList<String>> requestBody);

}