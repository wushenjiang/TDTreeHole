package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.database.DataBaseHelper;

import static java.lang.Thread.sleep;

public class LunchActivity extends AppCompatActivity {

    private DataBaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //app启动页的加载
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                //耗时任务，比如加载网络数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 这里可以睡几秒钟，如果要放广告的话
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } ).start();
        //初始化用户的登录状态
        initState();
    }

    private void initState() {
        Dao dao = new Dao(this);
        //查询数据，如果数据库中有数据，跳转至MainActivity
        if (dao.select_id()!=0){
            Intent intent = new Intent(LunchActivity.this, MainActivity.class);
            startActivity(intent);
            LunchActivity.this.finish();
        }
        //如果没有查询到数据，跳转至LoginActivity
        else {
            Intent intent = new Intent(LunchActivity.this, LoginActivity.class);
            startActivity(intent);
            LunchActivity.this.finish();
        }
    }

}
