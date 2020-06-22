package com.androidlearing.tdtreehole.api;

import com.androidlearing.tdtreehole.pojo.Repost;
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
 * @ClassName: RepostAPI
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/24 20:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/24 20:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface RepostAPI {
    @GET("repost/findAll")
    Call<List<Repost>> findAllReposts(@Query("postId") int postId);
    @POST("repost/repost")
    Call<Integer> insertRepost(@Body Repost repost);
}
