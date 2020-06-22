package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.api.UserAPI;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.httputils.RetrofitManager;
import com.androidlearing.tdtreehole.pojo.User;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mEt_username;
    private EditText mEt_password;
    private EditText mEt_repassword;
    private Button mBtn_register;
    private ImageView mIv_back;
    private TextView mTv_back;
    private Dao dao = new Dao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //隐藏默认头部标题栏
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        mEt_username = findViewById(R.id.et_username);
        mEt_password = findViewById(R.id.et_password);
        mEt_repassword = findViewById(R.id.et_repassword);
        mBtn_register = findViewById(R.id.btn_register);
        mIv_back = findViewById(R.id.iv_back);
        mTv_back = findViewById(R.id.tv_back);
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
    }

    private void setData() {
        User user = new User();
        user.setUsername(mEt_username.getText().toString());
        user.setPassword(mEt_password.getText().toString());
        user.setRepassword(mEt_repassword.getText().toString());
        if (TextUtils.isEmpty(user.getUsername())) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(user.getPassword())) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(user.getRepassword())) {
            Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_LONG).show();
        } else if (!user.getPassword().equals(user.getRepassword())) {
            Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
        } else {
            insertUser(user);
        }
    }

    private void insertUser(final User user) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<Integer> call = userAPI.register(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Integer result = response.body();
                    if (result != null) {
                        if (result == 1) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            //将用户信息存入本地数据库中
                            dao.insert(result, user.getUsername(), user.getPassword());
                            Log.d(TAG, "idKey == " + result);
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }else if(result == -1){
                            Toast.makeText(RegisterActivity.this,"用户名重复！请重新输入!",Toast.LENGTH_SHORT).show();
                        }
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
