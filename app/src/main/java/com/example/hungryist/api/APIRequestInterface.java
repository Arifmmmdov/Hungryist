package com.example.hungryist.api;


import com.example.hungryist.constants.Constants;

import java.util.List;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface APIRequestInterface {
    @GET("/currencies")
    Callback<List<Currency>> getCurrencies(@Header(Constants.KEY) String key, @Header(Constants.HOST) String header);

}
