package com.yztc.my.retrofit_test;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by My on 2016/10/17.
 */
public interface RetrofitApi {

    @GET("/")
     Call<ResponseBody> getHttp();

    @GET
    Call<ResponseBody> getUrL(@Url String string);

    @GET("square/{xxxx}")
    Call<ResponseBody>getPath(@Path("xxxx") String string);


      // http://localhost:8080/Test/MyTest?name=zhangsan
    //传递参数
    @GET("Test/MyTest")
    Call<ResponseBody> getMyTest(@Query("name") String param, @Query("clazz") String clazz);

    @GET("Test/MyTest")
    Call<ResponseBody> getMyTest(@QueryMap() Map<String, String> params);

    @FormUrlEncoded
    @POST("Test/MyTest")//用于提交一个参数的表单，必须使用POST方法，必须在方法中使用@FormUrlEncoded
    Call<ResponseBody> getMyFiled(@Field("name") String param, @Field("clazz") String clazz);

    @GET("Test/MyTest")
    Call<ResponseBody> getMyHead(@Header("token") String token);

    //上传文件需要注意的地方 1必须使用post方法 2必须使用Mulitipart注解 3上传文件的参数必须使用@part
    @Multipart
    @POST("Test/UploadFile")
    Call<ResponseBody> upload(@Part("ceshi") RequestBody body);


    @POST("Test/UploadFile")
    Call<ResponseBody> uploadFile(@Body RequestBody body);
    //    ![](http://ww1.sinaimg.cn/large/006y8lVajw1f8p8qab7dkj31kw0zjah5.jpg)

    @Streaming
    @POST
    Call<ResponseBody> downloadFile(@Url()String url);

    @GET
    @Streaming
    Call<ResponseBody> downloadImage(@Url() String url);

    @GET(UrlConfig.Path.GET_NEWS)
    Call<QuanminBean> getNews(@QueryMap() Map<String, String> params);


}
