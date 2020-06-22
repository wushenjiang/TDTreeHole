package com.androidlearing.tdtreehole.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.adapter.PostAndRepostAdapter;
import com.androidlearing.tdtreehole.api.PostAPI;
import com.androidlearing.tdtreehole.api.RepostAPI;
import com.androidlearing.tdtreehole.bean.ItemBean;
import com.androidlearing.tdtreehole.bean.ItemBeanRepost;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.Likes;
import com.androidlearing.tdtreehole.pojo.Post;
import com.androidlearing.tdtreehole.pojo.PostAndRepost;
import com.androidlearing.tdtreehole.pojo.Repost;
import com.jaren.lib.view.LikeView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RepostActivity extends AppCompatActivity {

    private static final String TAG = "RepostActivity";
    private static final int GET_REPORTS = 0;
    private static final int IS_INSERTED = 1;
    private static final int GET_POSTS = 2;
    private Post mPostForSelf;
    private XRecyclerView mRecyclerView;
    private Handler mHandler;
    private List<ItemBeanRepost> mItemBeanReposts;
    private List<PostAndRepost> mPostAndReposts = new ArrayList<>();
    private PostAndRepostAdapter mAdapter;
    private EditText mRepostContent;
    private String mContent;
    private int mIdKey;
    private ImageView iv_back;
    private TextView tv_back;
    private Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
    private LikeView mLikeView;
    private int mLikes;
    private boolean mIsLiked = false;
    private List<Likes> mLikeIds;
    private TextView mLike_tv;
    private Integer mPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏自带的标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_repost);
        //取得传来的数据
        Intent intent = getIntent();
        //mPostForSelf = intent.getParcelableExtra("postKey");
        mPostId = intent.getIntExtra("postId",0);
        Dao dao = new Dao(this);
        mIdKey = dao.select_id();
        Log.d(TAG, "mIdKey == " + mIdKey);
        //加载控件
        initView();
        //取得post
        getPost();
        //
        getLikes();
        //设置点击事件
        initListener();
        //设置刷新事件
        handleRefresh();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getPost() {
        PostAPI postAPI = retrofit.create(PostAPI.class);
        Call<Post> call = postAPI.findPostById(mPostId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "getPost: -->"+response.body().getPostid());
                Post post = response.body();
                Message message = new Message();
                message.what = GET_POSTS;
                message.obj = post;
                //设置该message的优先级为最高,即队列最前面
                mHandler.sendMessageAtFrontOfQueue(message);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: -->"+t.toString());
            }
        });
        //取得回帖数据
        getReposts();
        //处理数据，更新UI
        handleMsg();
    }

    //加载控件
    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_back = findViewById(R.id.tv_back);
        mRepostContent = this.findViewById(R.id.repost_content_tv);
        mRecyclerView = this.findViewById(R.id.recycler_view_for_repost);
        mLikeView = this.findViewById(R.id.like_view);
        mLike_tv = this.findViewById(R.id.likes_tv);
    }

    public void getLikeInt() {
        PostAPI postAPI = retrofit.create(PostAPI.class);
        Call<Integer> call = postAPI.getPostLikes(mPostId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d(TAG, "onResponse: -->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    mLikes = response.body();
                    Log.d(TAG, "getLikeInt: -->" + response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "getLikeInt-->onFailure: -->" + t.toString());
            }
        });
    }

    /**
     * 获取该帖子的点赞数和点赞id表
     * 如果该用户的id存在，则显示为已点赞，若不存在则显示为未点赞
     */
    private void getLikes() {
        PostAPI postAPI = retrofit.create(PostAPI.class);
        Call<Integer> call = postAPI.getPostLikes(mPostId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d(TAG, "onResponse: -->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    mLikes = response.body();
                    mLike_tv.setText(String.valueOf(mLikes));
                    Log.d(TAG, "onResponse: -->" + response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });
        Call<List<Likes>> callForIds = postAPI.getLikesId(mPostId);
        callForIds.enqueue(new Callback<List<Likes>>() {
            @Override
            public void onResponse(Call<List<Likes>> call, Response<List<Likes>> response) {
                Log.d(TAG, "onResponse: --->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    mLikeIds = response.body();
                    //去核对是否该用户的id存在于该列表
                    checkUserId(mLikeIds);
                    Log.d(TAG, "onResponse: -->" + mIsLiked);
                    mLikeView.setCheckedWithoutAnimator(mIsLiked);
                }
            }

            @Override
            public void onFailure(Call<List<Likes>> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });

    }

    private void checkUserId(List<Likes> likeIds) {
        if (likeIds != null) {
            for (Likes likes : likeIds) {
                Log.d(TAG, "checkUserId: -->" + likes.getUserid());
                if (mIdKey == likes.getUserid()) {
                    //如果存在则说明该用户已点赞
                    mIsLiked = true;
                    break;
                }
            }
            Log.d(TAG, "checkUserId: --->" + mIsLiked);
        }
    }

    /**
     * 初始化点击事件
     */
    private void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mLikeView.isChecked();
                int flag;
                //再获取一遍点赞数，防止有多人点赞时数据错乱
                getLikeInt();
                Log.d(TAG, "isChecked : -->" + isChecked);
                //没有点赞,去点赞
                if (!isChecked) {
                    //向数据库中存储数据
                    flag = 1;
                    PostAPI postAPI = retrofit.create(PostAPI.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("post", mPostForSelf);
                    map.put("flag", flag);
                    map.put("userId", mIdKey);
                    Call<Integer> call = postAPI.updateLikes(map);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Log.d(TAG, "!isChecked: -->" + response.code());
                            if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                                Integer result = response.body();
                                Log.d(TAG, "result: -->" + result);
                                if (result == 1) {
                                    mLike_tv.setText(String.valueOf(mLikes + 1));
                                    //设置为已点击
                                    mLikeView.toggle();
                                    mIsLiked = true;
                                    mLikeView.setChecked(mIsLiked);
                                } else {
                                    Toast.makeText(RepostActivity.this, "点赞失败!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d(TAG, "onFailure: -->" + t.toString());
                        }
                    });
                }
                //已经点赞,取消点赞
                if (isChecked) {
                    //向数据库中存储数据
                    flag = 0;
                    PostAPI postAPI = retrofit.create(PostAPI.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("post", mPostForSelf);
                    map.put("flag", flag);
                    map.put("userId", mIdKey);
                    Call<Integer> call = postAPI.updateLikes(map);
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Log.e(TAG, "isChecked: -->" + response.code());
                            if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                                Integer result = response.body();
                                Log.e(TAG, "result: -->" + result);
                                if (result == 1) {
                                    if (mLikes - 1 < 0) {
                                        mLike_tv.setText(String.valueOf(0));
                                    } else {
                                        mLike_tv.setText(String.valueOf(mLikes - 1));
                                        //设置为未点击
                                        mIsLiked = false;
                                        mLikeView.setChecked(mIsLiked);
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d(TAG, "onFailure: -->" + t.toString());
                        }
                    });
                }

            }
        });
    }

    //获取回复数据
    private void getReposts() {
        RepostAPI repostAPI = retrofit.create(RepostAPI.class);
        Call<List<Repost>> task = repostAPI.findAllReposts(mPostId);
        task.enqueue(new Callback<List<Repost>>() {
            @Override
            public void onResponse(Call<List<Repost>> call, Response<List<Repost>> response) {
                Log.d(TAG, "onResponse: -->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    List<Repost> reposts = response.body();
                    Message message = new Message();
                    message.what = GET_REPORTS;
                    message.obj = reposts;
                    mHandler.sendMessage(message);
                }

            }

            @Override
            public void onFailure(Call<List<Repost>> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });
    }

    //插入数据
    private void insertRepost(final Repost repost) {
        RepostAPI repostAPI = retrofit.create(RepostAPI.class);
        Call<Integer> call = repostAPI.insertRepost(repost);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d(TAG, "onResponse: -->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Integer result = response.body();
                    Message message = new Message();
                    message.what = IS_INSERTED;
                    message.obj = result;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: -->" + t.toString());
            }
        });
    }

    //处理刷新事件
    private void handleRefresh() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //复写上拉刷新
                getReposts();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                //复写下拉默认下拉方法。
            }
        });
    }

    //设置数据并更新UI
    private void setData() {
        //在设置数据前，如果有数据则先清空.防止加载重复数据
        if (mPostAndReposts != null) {
            mPostAndReposts.removeAll(mPostAndReposts);
        }
        PostAndRepost postAndRepost = new PostAndRepost();
        postAndRepost.setType(PostAndRepostAdapter.TYPE_POST_CONTENT);
        ItemBean itemBean = new ItemBean();
        Log.d(TAG, "setData: -->" + mPostForSelf.getUsername());
        itemBean.setUsername(mPostForSelf.getUsername());
        itemBean.setPosttime(mPostForSelf.getPosttime());
        itemBean.setContent(mPostForSelf.getPostcontent());
        itemBean.setTitle(mPostForSelf.getTitle());
        //对PostType做一个判断,放入正确的中文:
        //0是初始,1代表吐槽，2代表交友，3代表表白，4代表其他
        //一定要先判空,防止程序崩溃
        String type = "种类:";
        if (mPostForSelf.getPosttype() != null) {
            switch (Integer.parseInt(mPostForSelf.getPosttype())) {
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
        }
        itemBean.setPosttype(type);
        postAndRepost.setPost(itemBean);
        mPostAndReposts.add(postAndRepost);
        for (ItemBeanRepost item : mItemBeanReposts) {
            ItemBeanRepost items = new ItemBeanRepost();
            items.setName(item.getName());
            items.setContent(item.getContent());
            items.setPosttime(item.getPosttime());
            PostAndRepost postsAndReposts = new PostAndRepost();
            postsAndReposts.setType(PostAndRepostAdapter.TYPE_REPOST_CONTENT);
            postsAndReposts.setReposts(items);
            mPostAndReposts.add(postsAndReposts);
        }
        //数据设置完去更新UI
        showReposts();
        //设置UI的样式
        setRepostsStyle();
    }

    //设置UI样式
    private void setRepostsStyle() {
        //设置是否允许下拉刷新
        mRecyclerView.setPullRefreshEnabled(true);
        //设置是否允许上拉加载
        mRecyclerView.setLoadingMoreEnabled(false);
        //设置样式
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseRise);
    }

    //更新UI
    private void showReposts() {
        Log.d(TAG, "showReposts...");
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置水平还是竖直
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置标准还是逆向
        layoutManager.setReverseLayout(false);
        //布局管理器放入RecyclerView
        mRecyclerView.setLayoutManager(layoutManager);
        //创建适配器
        mAdapter = new PostAndRepostAdapter(mPostAndReposts);
        //设置前判断是否有Adapter,若不为空则移除所有数据再添加
        if (mRecyclerView.getAdapter() != null) {
            mRecyclerView.removeAllViews();
        }
        //设置进去
        mRecyclerView.setAdapter(mAdapter);
        //设置点赞状态
        Log.d(TAG, "showReposts: -->" + mIsLiked);
//        mLikeView.setHasLike(mIsLiked);

    }

    //取得对应的数据并处理
    private void handleMsg() {
        mHandler = new Handler(new Handler.Callback() {
            boolean isHandled = false;
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case GET_REPORTS:
                        List<Repost> repostList = (List<Repost>) msg.obj;
                        List<ItemBeanRepost> itemBeanReposts = new ArrayList<>();
                        for (Repost repost : repostList) {
                            ItemBeanRepost itemBeanRepost = new ItemBeanRepost();
                            itemBeanRepost.setName(repost.getUsername());
                            itemBeanRepost.setPosttime(repost.getPublishtime());
                            itemBeanRepost.setContent(repost.getContent());
                            itemBeanReposts.add(itemBeanRepost);
                        }
                        mItemBeanReposts = itemBeanReposts;
                        //给适配器设置数据
                        Log.d(TAG, "GET_REPORTS: -->"+mPostForSelf.getPostid());
                        setData();
                        isHandled = true;
                        break;
                    case IS_INSERTED:
                        int result = (Integer) msg.obj;
                        //大于0代表插入成功,更新列表
                        if (result > 0) {
                            getReposts();
                            mRecyclerView.refreshComplete();
                        }
                        break;
                    case GET_POSTS:
                        mPostForSelf = (Post) msg.obj;
                        isHandled = true;
                        break;
                }
                return isHandled;
            }
        });
    }


    public void TestRepost(View view) {
        Log.d(TAG, "onClick...");
        mContent = mRepostContent.getText().toString();
        int postid = mPostForSelf.getPostid();
        Log.d(TAG, "content ==" + mContent);
        //设置日期格式
        DateFormat dateFormat = DateFormat.getDateInstance(2);
        //获取当前时间
        String publishtime = dateFormat.format(new Date());
        //简单的判空处理
        if (TextUtils.isEmpty(mContent)) {
            Toast.makeText(RepostActivity.this, "请输入回复内容!", Toast.LENGTH_SHORT).show();
            return;
        }
        final Repost repost = new Repost();
        repost.setPostid(postid);
        repost.setContent(mContent);
        repost.setPublishtime(publishtime);
        repost.setModifytime(publishtime);
        repost.setUserid(mIdKey);
        //去插入数据
        insertRepost(repost);
        //隐藏输入法
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mRepostContent.getWindowToken(), 0);
        }
    }
}
