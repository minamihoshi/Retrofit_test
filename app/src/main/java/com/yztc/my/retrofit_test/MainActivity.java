package com.yztc.my.retrofit_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
  private Button mbtn,mbtn2;
    private RetrofitApi api;
private ImageView iv;
    private ProgressBar progressbar;
    private Bitmap bitmap;
    private int lensum;
    private  long length;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==1){
                progressbar.setProgress(msg.arg1);
                Log.e("TAG", "handleMessage: "+msg.arg1 );
            }
            if(msg.what==2){
                iv.setImageBitmap(bitmap);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtn = (Button) findViewById(R.id.btn);
        mbtn2 = (Button) findViewById(R.id.btn2);
        iv = (ImageView) findViewById(R.id.iv);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.baidu.com/").build();
       api = retrofit.create(RetrofitApi.class);
        mbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpHelper helper = HttpHelper.getInstance();
                helper.getImage("http://pics.sc.chinaz.com/files/pic/pic9/201610/apic23676.jpg", new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody body = response.body();
                         length = body.contentLength();
                        Log.e("TAG", "onResponse: "+length);
                        final InputStream inputStream = body.byteStream();

                        final BufferedInputStream bis = new BufferedInputStream(inputStream);
                        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Log.e("TAG", "onResponse: "+Thread.currentThread().getName() );

                        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/aaa.jpg";
                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(path);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        final FileOutputStream finalOs = os;
                        new Thread(new Runnable() {
                           @Override
                           public void run() {
                               try {
                                   byte [] temp = new byte[1024];
                                   int len = 0;
                                   while ((len =bis.read(temp))!=-1){

                                       Thread.sleep(100);
                                       baos.write(temp,0,len);
                                       finalOs.write(temp,0,len);
                                       lensum+=len;
                                       int percent = (int) (lensum*100/length);
                                       Log.e("TAG", "run: "+percent );
                                       Message message = new Message();
                                       message.what=1;
                                       message.arg1=percent;
                                       handler.sendMessage(message);

                                   }
                                   byte[] bytes = baos.toByteArray();
                                   bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                   finalOs.flush();
                                   baos.flush();
                                   finalOs.close();
                                   baos.close();
                                   inputStream.close();
                                   Message message = new Message();
                                   message.what=2;
                                   handler.sendMessage(message);

                               } catch (IOException e) {
                                   e.printStackTrace();
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }

                           }
                       }).start();


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               HttpHelper helper = HttpHelper.getInstance();
                Map<String,String> params = new HashMap<String, String>();
                params.put(UrlConfig.Param.ID, "12");
                params.put(UrlConfig.Param.VERSION, UrlConfig.DefaultValue.VERSION);
                params.put(UrlConfig.Param.PLAT, UrlConfig.DefaultValue.PLAT);
                params.put(UrlConfig.Param.PAGE, UrlConfig.DefaultValue.PAGE);
                helper.getNews(params, new Callback<QuanminBean>() {
                    @Override
                    public void onResponse(Call<QuanminBean> call, Response<QuanminBean> response) {
                        QuanminBean body = response.body();
                        String num = body.getThisPageNum();
                        Log.e("TAG", "onResponse: "+num );
                        mbtn.setText(String.valueOf(num));
                    }

                    @Override
                    public void onFailure(Call<QuanminBean> call, Throwable t) {

                    }
                });




            }
        });
    }

    private void post() {
        Call<ResponseBody> call = api.getUrL("www.sina.com.cn/");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    String string = body.string();
                    mbtn.setText(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getHttp(Response<ResponseBody> response) {
        Call<ResponseBody> call = api.getHttp();
        ResponseBody body = response.body();
        try {
            String string = body.string();
            String name = Thread.currentThread().getName();
            mbtn.setText(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
