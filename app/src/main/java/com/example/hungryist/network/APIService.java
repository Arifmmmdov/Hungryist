package com.example.hungryist.network;


import com.example.hungryist.constants.BundleConstants;

import java.util.List;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;

interface APIService {

    @GET("/currencies")
    Callback<List<Currency>> getCurrencies(@Header(BundleConstants.KEY) String key,@Header(BundleConstants.HOST) String header);

}
