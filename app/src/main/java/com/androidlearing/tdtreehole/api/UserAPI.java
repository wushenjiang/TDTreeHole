package com.androidlearing.tdtreehole.api;

import com.androidlearing.tdtreehole.pojo.Post;
import com.androidlearing.tdtreehole.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.api
 * @ClassName: UserAPI
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/24 20:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/24 20:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface UserAPI {
    @POST("user/login")
    Call<Integer> login(@Body User user);
    @POST("user/register")
    Call<Integer> register(@Body User user);
    @GET("user/findPost")
    Call<List<Post>> getPostById(@Query("userId") Integer userid);
    @POST("user/updateUser")
    Call<Integer> update(@Body User user);
}
