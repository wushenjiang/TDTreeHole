package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText mEt_username;
    private EditText mEt_password;
    private Button mBtn_login;
    Dao dao = new Dao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏默认头部标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initView();
        //this.getExternalFilesDir(Environment.DIRECTORY_ALARMS);
    }

    private void initView() {
        mEt_username = findViewById(R.id.et_username);
        mEt_password = findViewById(R.id.et_password);
        mBtn_login = findViewById(R.id.btn_login);
        mBtn_login.setOnClickListener(new View.OnClickListener() {
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
        if (user.getUsername().equals("")){
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
        }else if (user.getPassword().equals("")){
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
        }else {
            insertUser(user);
        }
    }

    private void insertUser(final User user) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<Integer> call = userAPI.login(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Integer result = response.body();
                    if(result !=null){
                        if(result >0){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            dao.insert(result,user.getUsername(),user.getPassword());
                            Toast.makeText(LoginActivity.this,"登录成功!", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"用户名或密码错误!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this,"用户名或密码错误!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: -->"+t.toString());
            }
        });

    }

    public void Register(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
