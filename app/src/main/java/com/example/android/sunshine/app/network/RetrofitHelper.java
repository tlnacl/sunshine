package com.example.android.sunshine.app.network;

import com.example.android.sunshine.app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by tlnacl on 15/01/15.
 */
public final class RetrofitHelper {
    private static RestAdapter restAdapter;
    private static final String API_URL = BuildConfig.API_ENDPOINT;

    public RetrofitHelper() {
    }

    public static RestAdapter getRestAdapter(){
        //set timeout for error test
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.interceptors().add(statusInterceptor);
//        client.setRetryOnConnectionFailure(true);
        if(restAdapter==null){
            Gson gson = new GsonBuilder().setDateFormat("ddMMyyyy HH:mm:ss").create();
            restAdapter = new RestAdapter.Builder().setEndpoint(API_URL)
                    .setErrorHandler(new RetrofitErrorHandler())
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson))
                    .setClient(new OkClient(client))
                    .build();
        }
        return  restAdapter;
    }

    public static OpenWeatherServer getServerApi(){
        return getRestAdapter().create(OpenWeatherServer.class);
    }

    private static Interceptor statusInterceptor = new Interceptor() {
        @Override
        public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) throws IOException {

            com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
            if (originalResponse.code() != HttpURLConnection.HTTP_OK) {
                //TODO retry
//                chain.request().
            }
                return originalResponse;
            }
        };

}
