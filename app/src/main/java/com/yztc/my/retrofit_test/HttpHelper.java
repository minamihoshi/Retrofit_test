package com.yztc.my.retrofit_test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by My on 2016/10/17.
 */
public class HttpHelper {
  private  static  volatile HttpHelper helper ;
    private Retrofit mretrofit;
    private Gson mGson;
 private  RetrofitApi api;
  private HttpHelper(){
       initRetrofit();
       initApi();
  }

    private void initApi() {
          api = mretrofit.create(RetrofitApi.class);
    }

    private void initGson() {
        mGson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    private void initRetrofit() {
        initGson();
    mretrofit = new Retrofit.Builder().baseUrl(UrlConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build();

    }

    public static  HttpHelper getInstance(){

        if(helper==null){
            synchronized (HttpHelper.class){
                if(helper==null){
                    helper = new HttpHelper();
                }
            }
        }

        return helper;
    }


    public void getNews(Map<String,String> params, Callback<QuanminBean> callback){
        Call<QuanminBean> call = api.getNews(params);
        call.enqueue(callback);

    }

    public void getImage(String url,Callback<ResponseBody> callback){
        Call<ResponseBody> call = api.downloadImage(url);
        call.enqueue(callback);
    }

}
