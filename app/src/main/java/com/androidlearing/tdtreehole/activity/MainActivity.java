package com.androidlearing.tdtreehole.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.database.Dao;
import com.androidlearing.tdtreehole.fragment.Fragment_Index;
import com.androidlearing.tdtreehole.fragment.Fragment_My;
import com.androidlearing.tdtreehole.fragment.Fragment_Star;
import com.androidlearing.tdtreehole.fragment.Fragment_Three;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private RadioButton index_tab,star_tab,contact_tab,my_tab;
    private ImageView sign_iv;
    private TextView titlebar_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏头部标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //将主页添加到第一个fragment中，即默认fragment
        Fragment fragment_index = new Fragment_Index();
        titlebar_tv.setText("主页");
        transaction.add(R.id.fragment_container,fragment_index);
        transaction.commit();
    }


    private void initView() {
        index_tab = findViewById(R.id.index_tab);
        star_tab =  findViewById(R.id.star_tab);
        contact_tab =  findViewById(R.id.contact_tab);
        my_tab =  findViewById(R.id.my_tab);
        sign_iv =  findViewById(R.id.sign_iv);
        //自定义的头部标题框
        titlebar_tv = findViewById(R.id.tv_titlebar);
        index_tab.setOnClickListener(this);
        star_tab.setOnClickListener(this);
        contact_tab.setOnClickListener(this);
        my_tab.setOnClickListener(this);
        sign_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            //通过switch判断用哪个fragment替换当前的fragment
            case R.id.index_tab:
                titlebar_tv.setText("首页");
                Fragment fragment_index = new Fragment_Index();
                transaction.replace(R.id.fragment_container,fragment_index);
                break;
            case R.id.star_tab:
                titlebar_tv.setText("星球");
                transaction.replace(R.id.fragment_container,new Fragment_Star());
                break;
            case R.id.contact_tab:
                titlebar_tv.setText("铁大树洞");
                transaction.replace(R.id.fragment_container,new Fragment_Three());
                break;
            case R.id.my_tab:
                //跳转到"我的"fragment
                titlebar_tv.setText("我");
                Fragment fragment_my = new Fragment_My();
                transaction.replace(R.id.fragment_container,fragment_my);
                break;
                //点击绑定的图片，跳转至发布的Activity
            case R.id.sign_iv:
                Intent postIntent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(postIntent);
                //添加Activity跳转的自定义的动画，zoom_in为进入动画，zoom_out为退出动画
                //overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
                break;
        }
        transaction.commit();
    }
}
