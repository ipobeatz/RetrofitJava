package com.example.retrofitjava.service;

import com.example.retrofitjava.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {


    @GET("Cevik10/AS10-CryptoCurrencyRetrofit/master/sparecrypto.json")
    Call<List<CryptoModel>> getData();
}
