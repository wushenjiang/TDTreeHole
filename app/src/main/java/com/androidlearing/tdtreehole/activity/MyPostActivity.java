package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.adapter.PostForSelfAdapter;
import com.androidlearing.tdtreehole.api.UserAPI;
import com.androidlearing.tdtreehole.bean.ItemSelfPostBean;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.Post;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPostActivity extends AppCompatActivity {
    private static final String TAG = "MyPostActivity";
    private static final int GET_POSTS_SELF = 0;
    private int mIdKey;
    private Handler mHandler;
    private List<Post> postPastList;
    private List<ItemSelfPostBean> postList;
    private XRecyclerView mXRecyclerView;
    private PostForSelfAdapter mAdapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_send);
        mXRecyclerView = findViewById(R.id.recycler_view_my_send);
        Dao dao = new Dao(this);
        mIdKey = dao.select_id();
        Log.d(TAG, "idKey ==" + mIdKey);
        //取得数据
        getPosts();
        //处理数据
        handleMsg();
    }
    //处理数据
    private void handleMsg() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                boolean isHandled = false;
                switch (msg.what) {
                    case GET_POSTS_SELF:
                        if (msg.obj != null) {
                            List<Post> post_list_temp = (List<Post>) msg.obj;
                            postPastList = post_list_temp;
                            List<ItemSelfPostBean> itemSelfPostBeans = new ArrayList<>();
                            for (Post post : post_list_temp) {
                                ItemSelfPostBean itemSelfPostBean = new ItemSelfPostBean();
                                itemSelfPostBean.setTitle(post.getTitle());
                                String type = "种类:";
                                switch (Integer.parseInt(post.getPosttype())) {
                                    case 0:
                                        break;
                                    case 1:
                                        type += "吐槽";
                                        break;
                                    case 2:
                                        type += "交友";
                                        break;
                                    case 3:
                                        type += "表白";
                                        break;
                                    case 4:
                                        type += "其他";
                                        break;
                                }
                                itemSelfPostBean.setType(type);
                                itemSelfPostBean.setContent(post.getPostcontent());
                                itemSelfPostBean.setTime(post.getPosttime());
                                itemSelfPostBeans.add(itemSelfPostBean);
                            }
                            //判断List是否为空,若不空则清空后再添加
                            if (postList != null) {
                                postList = null;
                            }
                            postList = itemSelfPostBeans;
                            showPosts();
                            isHandled = true;
                        }
                        break;
                }
                return isHandled;
            }
        });
    }
    //显示数据
    private void showPosts() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyPostActivity.this);
        layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PostForSelfAdapter(postList);
        mAdapter.setOnItemClickListener(new PostForSelfAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyPostActivity.this, RepostActivity.class);
                Post post = postPastList.get(position);
                intent.putExtra("postId", post.getPostid());
                Log.d(TAG,""+post.getPostid());
                startActivity(intent);
            }
        });
        if (mXRecyclerView.getAdapter() != null) {
            mXRecyclerView.removeAllViews();
        }
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScaleRippleMultiple);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新事件
                getPosts();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mXRecyclerView.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
            }
        });

    }

    //取得发帖数据
    private void getPosts() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Post>> call = userAPI.getPostById(mIdKey);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "onResponse:  -->"+response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    List<Post> postList = response.body();
                    //使用Handler和message来请求主线程更新UI
                    Message message = new Message();
                    message.what = GET_POSTS_SELF;
                    message.obj = postList;
                    mHandler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure:  -->"+t.toString());
            }
        });
    }
}
