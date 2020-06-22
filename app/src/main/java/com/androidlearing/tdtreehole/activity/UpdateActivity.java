package com.androidlearing.tdtreehole.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import org.w3c.dom.Text;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "UpdateActivity";
    private EditText mEt_username,mEt_password,mEt_password_new;
    private ImageView iv_back;
    private TextView tv_back;
    private TextView update_tv;
    private Dao dao = new Dao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏默认头部标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        mEt_username = findViewById(R.id.et_username);
        mEt_password = findViewById(R.id.et_password);
        mEt_password_new = findViewById(R.id.et_password_new);
        iv_back = findViewById(R.id.iv_back);
        tv_back = findViewById(R.id.tv_back);
        update_tv = findViewById(R.id.update_tv);
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        update_tv.setOnClickListener(this);
        mEt_username.setText(dao.select_username());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.update_tv:
                Judge();
                break;
        }
    }

    //判断用户输入并更新数据
    private void Judge() {
        if (TextUtils.isEmpty(mEt_username.getText())){
            Toast.makeText(this,"用户名不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty((mEt_password_new).getText())){
            Toast.makeText(this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }else if (!mEt_password.getText().toString().equals(dao.select_password())){
            Toast.makeText(this,"原密码不正确!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            //获取数据
            int id = dao.select_id();
            String username = mEt_username.getText().toString();
            String password = mEt_password_new.getText().toString();
            //更新数据库中的数据
            User user = new User();
            user.setId(id);
            user.setPassword(password);
            user.setUsername(username);
            Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call<Integer> call = userAPI.update(user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.d(TAG, "Judge --> onResponse:  -->"+response.code());
                    if(response.code() == HttpsURLConnection.HTTP_OK&&response.body() != null){
                        int result = response.body();
                        if(result==1){
                            //更新Sqlite的数据
                            dao.delete();
                            dao.insert(id,username,password);
                            Toast.makeText(UpdateActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(TAG, "onFailure: --> "+t.toString());
                }
            });
        }
    }
}
