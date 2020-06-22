package com.androidlearing.tdtreehole.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.adapter.PostForTypeAdapter;
import com.androidlearing.tdtreehole.adapter.RecyclerViewBaseAdapter;
import com.androidlearing.tdtreehole.api.PostAPI;
import com.androidlearing.tdtreehole.bean.ItemBean;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.Post;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StarActivity";
    private static final int GET_POSTS_BY_TYPE = 0;
    private XRecyclerView mRecyclerView;
    private int mPosttype;
    Handler mHandler;
    private List<Post> postPastList;
    private List<ItemBean> postList;
    private PostForTypeAdapter mAdapter;
    private TextView mTitleBar;
    private ImageView iv_back;
    private TextView tv_back;
    private Retrofit mRetrofit;
    private PostAPI mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏默认头部标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_star);
        Intent intent = getIntent();
        //取得种类
        mPosttype = intent.getIntExtra("posttype", 0);
        //初始化组件
        initView();
        //取得数据
        getPostsByType();
        //处理数据,更新UI
        handleMsg();
    }

    //处理数据,通知主线程更新UI
    private void handleMsg() {

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Boolean isHandled = false;
                switch (msg.what) {
                    case GET_POSTS_BY_TYPE:
                        if (msg.obj != null) {
                            List<Post> postListForTemp = (List<Post>) msg.obj;
                            List<ItemBean> itemBeanList = new ArrayList<>();
                            postPastList = postListForTemp;
                            for (Post post : postListForTemp) {
                                ItemBean itemBean = new ItemBean();
                                itemBean.setTitle(post.getTitle());
                                itemBean.setPosttype(post.getPosttype());
                                itemBean.setPosttime(post.getPosttime());
                                itemBean.setContent(post.getPostcontent());
                                itemBean.setLikes(post.getLikes());
                                itemBean.setComments(post.getComments());
                                itemBeanList.add(itemBean);
                                Log.d(TAG, "handleMessage: -->"+itemBean.getComments());
                            }
                            //判断list是否为空,若不为空则清空后再添加数据
                            if (postList != null) {
                                postList = null;
                            }
                            postList = itemBeanList;
                            //去更新UI
                            showPosts();
                            isHandled = true;
                        }
                }
                return isHandled;
            }

        });
    }

    //显示瀑布流
    private void showPosts() {
        //准备布局管理器,一列,竖向排列
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器方向
        layoutManager.setReverseLayout(false);
        //设置布局管理器到RecyclerView里
        mRecyclerView.setLayoutManager(layoutManager);
        //创建适配器
        mAdapter = new PostForTypeAdapter(postList);
        //判断适配器是否为空,如果不为空则清空后再添加数据
        if (mRecyclerView.getAdapter() != null) {
            mRecyclerView.removeAllViews();
        }
        //设置适配器
        mRecyclerView.setAdapter(mAdapter);
        //为RecyclerView设置样式和点击事件
        setPostsStyle();
    }

    //设置样式及事件
    private void setPostsStyle() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //刷新的回调
                getPostsByType();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2500);
            }

            @Override
            public void onLoadMore() {
                //下拉加载更多的回调
                getPostsByType();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                    }
                }, 2500);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //处理条目点击事件
                Intent intent = new Intent(StarActivity.this, RepostActivity.class);
                Post post = postPastList.get(position);
                intent.putExtra("postId", post.getPostid());
                Log.d(TAG, "postKey == " + post.getPosttype());
                startActivity(intent);
            }
        });
    }

    //通过种类取得数据
    private void getPostsByType() {
        mRetrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = mRetrofit.create(PostAPI.class);
        Call<List<Post>> task = mApi.findPostsByType(mPosttype);
        task.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    List<Post> postList = response.body();
                    Message message = new Message();
                    message.what = GET_POSTS_BY_TYPE;
                    message.obj = postList;
                    mHandler.sendMessageAtFrontOfQueue(message);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });
    }

    //初始化组件
    private void initView() {
        mTitleBar = this.findViewById(R.id.tv_titlebar);
        switch (mPosttype) {
            case 1:
                mTitleBar.setText("吐槽");
                break;
            case 2:
                mTitleBar.setText("交友");
                break;
            case 3:
                mTitleBar.setText("表白");
                break;
            case 4:
                mTitleBar.setText("其他");
                break;
        }
        mRecyclerView = this.findViewById(R.id.recycler_view_for_star);
        iv_back = findViewById(R.id.iv_back);
        tv_back = findViewById(R.id.tv_back);
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
