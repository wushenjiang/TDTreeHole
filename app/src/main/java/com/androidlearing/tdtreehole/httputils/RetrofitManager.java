package com.androidlearing.tdtreehole.httputils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.httputils
 * @ClassName: RetrofitMannger
 * @Description: 单例设计模式,产生单例Retrofit对象
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/24 19:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/24 19:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RetrofitManager {
    public static final String BASE_URL = "http://39.97.109.245/TDTreeHoleAPI/";
    public static final int CONNECT_TIME_OUT = 10000;//毫秒
    private Retrofit mRetrofit;

    private RetrofitManager() {
       createRetrofit();
    }

    private void createRetrofit() {
        //设置一下okHttp的参数
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)//设置BaseUrl
                .client(okHttpClient)//设置请求的client
                .addConverterFactory(GsonConverterFactory.create())//设置转换器
                .build();
    }
    private static RetrofitManager sRetrofitManager = null;
    public static RetrofitManager getInstance(){
        if(sRetrofitManager == null){
            synchronized (RetrofitManager.class){
                if(sRetrofitManager == null){
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }
    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
