package com.androidlearing.tdtreehole.api;

import com.androidlearing.tdtreehole.pojo.Likes;
import com.androidlearing.tdtreehole.pojo.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.api
 * @ClassName: PostAPI
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/24 19:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/24 19:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface PostAPI {
    @GET("post/findAll")
    Call<List<Post>> findAllPosts();
    @GET("post/findByType")
    Call<List<Post>> findPostsByType(@Query("postType") Integer postType);
    @GET("post/findPostById")
    Call<Post> findPostById(@Query("postId") Integer postId);
    @POST("post/addPost")
    Call<Integer> insertPost(@Body Post post);
    @POST("post/updateLike")
    Call<Integer> updateLikes(@Body Map<String,Object> map);
    @GET("post/findLikes")
    Call<List<Likes>> getLikesId(@Query("postId") Integer postId);
    @GET("post/findPostLikes")
    Call<Integer> getPostLikes(@Query("postId") Integer postId);
    @GET("post/findRepostCount")
    Call<Integer> getPostComments(@Query("postId") Integer postId);
}
