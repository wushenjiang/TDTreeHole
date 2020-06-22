package com.androidlearing.tdtreehole.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.activity.RepostActivity;
import com.androidlearing.tdtreehole.adapter.PostIndexAdapter;
import com.androidlearing.tdtreehole.adapter.RecyclerViewBaseAdapter;
import com.androidlearing.tdtreehole.api.PostAPI;
import com.androidlearing.tdtreehole.bean.ItemBean;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.Post;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 该Fragment用来显示主页
 */
public class Fragment_Index extends Fragment {

    private static final String TAG = "Fragment_Index";
    private static final int GET_POSTS = 0;
    private RecyclerView mList;
    private Gson mGson = new Gson();
    private Handler mHandler;
    private List<ItemBean> postList;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private View mRootView;
    private List<Post> postPastList;
    private int mIdKey;
    private boolean mIsLiked;
    private Post mPost = new Post();
    private Retrofit mRetrofit;
    private PostAPI mApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从数据库中读取id
        Dao dao = new Dao(getActivity());
        mIdKey =dao.select_id();
        Log.d(TAG, "onCreate: id -->"+mIdKey);
        //取得数据
        getPosts();
        //处理数据，更新UI
        handleMsg();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_index, container, false);
        //初始化rootView里控件
        mList = mRootView.findViewById(R.id.recycler_view);
        mRefreshLayout = mRootView.findViewById(R.id.refresh_layout);
        //处理下拉刷新
        handlerDownPullUpdate();
        return mRootView;

    }

    /**
     * 处理下拉刷新事件
     */
    private void handlerDownPullUpdate() {
        //设置刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent,R.color.colorMain);
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(() -> {
            //在这里去执行刷新数据的操作
            getPosts();
            handleMsg();
            //更新UI
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //使刷新停止,更新列表
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.setRefreshing(false);
                }
            }, 1500);
        });
    }

    /**
     * 展示数据
     */
    private void showPosts() {
        //准备布局管理器,一列,竖向排列
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器方向
        layoutManager.setReverseLayout(false);
        //设置布局管理器到RecyclerView里
        mList.setLayoutManager(layoutManager);
        //创建适配器
        mAdapter = new PostIndexAdapter(postList);
        //判断适配器是否为空,如果不为空则清空后再添加数据
        if(mList.getAdapter()!=null){
            mList.removeAllViews();
        }
        //设置适配器
        mList.setAdapter(mAdapter);
        //设置监听事件
        initRecyclerListener();

    }

    /**
     * get方法异步取得数据
     */
    private void getPosts() {
        mRetrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = mRetrofit.create(PostAPI.class);
        Call<List<Post>> posts = mApi.findAllPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    List<Post> posts = response.body();
                    Message message = new Message();
                    message.what = GET_POSTS;
                    message.obj = posts;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });

    }
    /**
     * 处理msg
     */
    private void handleMsg() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Boolean isHandled = false;
                switch(msg.what){
                    case GET_POSTS:
                        List<Post> post_list = (List<Post>) msg.obj;
                        List<ItemBean> postItemBean = new ArrayList<>();
                        postPastList = post_list;
                        if(post_list!=null){
                            for(Post post:post_list){
                                ItemBean itemBean = new ItemBean();
                                itemBean.setTitle(post.getTitle());
                                itemBean.setContent(post.getPostcontent());
                                itemBean.setPosttime(post.getPosttime());
                                itemBean.setPosttype(post.getPosttype());
                                itemBean.setLikes(post.getLikes());
                                itemBean.setComments(post.getComments());
                                postItemBean.add(itemBean);
                            }

                            //判断List是否为空,若不空则清空后再添加
                            if (postList != null) {
                                postList = null;
                            }
                            postList = postItemBean;
                            showPosts();
                            isHandled = true;
                        }
                        break;
                }
                Log.d(TAG,"isHandler-->"+isHandled);
                return isHandled;
            }
        });
    }

    //设置RecyclerView的监听事件
    private void initRecyclerListener() {
        //设置条目点击事件
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), RepostActivity.class);
                        Post post = postPastList.get(position);
                        intent.putExtra("postKey", post);
                        intent.putExtra("postId",post.getPostid());
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
