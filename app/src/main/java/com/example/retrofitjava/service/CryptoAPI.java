package com.example.retrofitjava.service;

import com.example.retrofitjava.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {


    @GET("prices?key=b55bc9dce756e2fdf0b16f5b3c545f86083b9bbd")
    Call<List<CryptoModel>> getData();
}
