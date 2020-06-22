package com.androidlearing.tdtreehole.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.activity.MyPostActivity;
import com.androidlearing.tdtreehole.activity.UpdateActivity;
import com.androidlearing.tdtreehole.activity.LoginActivity;
import com.androidlearing.tdtreehole.database.Dao;

public class Fragment_My extends Fragment implements View.OnClickListener {

    private static final String TAG = "Fragment_My";
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private int mIdKey;
    private Bundle mBundle_send;
    private TextView tv_username;
    private Button btn_signout,btn_about;
    private Button mBtn_post;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //由于实现Fragment的嵌套，这里获取的是子Fragment管理器
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        //取得从MainActivity传来的id值与用户名
        Dao dao = new Dao(getActivity());
        mIdKey = dao.select_id();
        Log.d(TAG,"idKey =="+ mIdKey);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        //用户名
        tv_username = rootView.findViewById(R.id.tv_username);
        Dao dao = new Dao(getContext());
        tv_username.setText(dao.select_username());
        //修改信息键
        btn_about = rootView.findViewById(R.id.btn_about);
        btn_about.setOnClickListener(this);
        //我的发帖键
        mBtn_post = rootView.findViewById(R.id.my_post);
        mBtn_post.setOnClickListener(this);
        //退
        // 出登录按键
        btn_signout = rootView.findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.btn_signout:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                //删除用户数据
                Dao dao = new Dao(getContext());
                dao.delete();
                getActivity().finish();
                break;
            case R.id.btn_about:
                Intent intent1 = new Intent(getActivity(), UpdateActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_post:
                Intent intent2 = new Intent(getActivity(), MyPostActivity.class);
                startActivity(intent2);
                break;
        }
        transaction.commit();
    }
}
