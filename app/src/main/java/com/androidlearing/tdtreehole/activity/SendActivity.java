package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.api.PostAPI;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.Post;

import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SendActivity";
    private TextView et_title,et_content;
    private RadioButton rb_complain,rb_love,rb_friends,rb_other;
    private TextView send_tv;
    private String title,content,time,type="";
    private int mIdKey;
    private ImageView iv_back;
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏默认头部标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_send);
        initView();
        //取得id值
        Dao dao = new Dao(this);
        mIdKey = dao.select_id();
    }

    private void initView() {
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        rb_complain = findViewById(R.id.rb_complain);
        rb_friends = findViewById(R.id.rb_friends);
        rb_love = findViewById(R.id.rb_love);
        rb_other = findViewById(R.id.rb_other);
        send_tv = findViewById(R.id.send_tv);
        iv_back = findViewById(R.id.iv_back);
        tv_back = findViewById(R.id.tv_back);
        rb_complain.setOnClickListener(this);
        rb_friends.setOnClickListener(this);
        rb_love.setOnClickListener(this);
        rb_other.setOnClickListener(this);
        send_tv.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_complain:
                type="1";
                break;
            case R.id.rb_love:
                type="3";
                break;
            case R.id.rb_friends:
                type="2";
                break;
            case R.id.rb_other:
                type="4";
                break;
            case R.id.send_tv:
                PutData();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void PutData() {
        title = et_title.getText().toString();
        content = et_content.getText().toString();
        int userid=mIdKey;
        if(TextUtils.isEmpty(title)){
            Toast.makeText(this,"标题不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(content)){
            Toast.makeText(this,"内容不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(type)){
            Toast.makeText(this,"种类不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }else if(title.length()>=12){
            Toast.makeText(this,"标题过长,请重新输入!",Toast.LENGTH_SHORT).show();
            return;
        }
        //设置日期格式
        DateFormat dateFormat = DateFormat.getDateInstance(2);
        //获取当前时间
        time =dateFormat.format(new Date());
        insertPost(title,content,time,userid,type);
    }

    private void insertPost(final String title, final String content, final String time, final int userid, final String type) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        PostAPI api = retrofit.create(PostAPI.class);
        Post post = new Post();
        post.setTitle(title);
        post.setModifytime(time);
        post.setPosttime(time);
        post.setPostcontent(content);
        post.setUserid(userid);
        post.setPosttype(type);
        post.setPostid(0);
        Log.d(TAG, "insertPost: post -->"+post);
        Call<Integer> task = api.insertPost(post);
        task.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d(TAG, "code -->"+response.code());
                Log.d(TAG, "onResponse:  -->"+response.body());
                if( response.code() == HttpURLConnection.HTTP_OK){
                    Integer result = response.body();
                    if(result ==1){
                        Intent intent = new Intent(SendActivity.this,MainActivity.class);
                        intent.putExtra("idKey",mIdKey);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SendActivity.this, "发帖成功!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SendActivity.this,"发帖失败！",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: -->"+t.toString());
            }
        });
    }
}
