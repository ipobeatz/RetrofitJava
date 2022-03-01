package com.example.retrofitjava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retrofitjava.R;
import com.example.retrofitjava.adapter.RecyclerViewAdapter;
import com.example.retrofitjava.model.CryptoModel;
import com.example.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);






        Gson gson = new GsonBuilder().setLenient().create();

        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();



    }


    private void loadData(){
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
    @Override
    public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
    if(response.isSuccessful()){
        List<CryptoModel> responseList = response.body();
        cryptoModels = new ArrayList<>(responseList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    }

    @Override
    public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
        t.printStackTrace();

    }


});
    }


    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 2000);



    }




    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    //https://api.nomics.com/v1/prices?key=b55bc9dce756e2fdf0b16f5b3c545f86083b9bbd
}