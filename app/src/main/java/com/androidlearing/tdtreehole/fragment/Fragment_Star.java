package com.androidlearing.tdtreehole.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.activity.StarActivity;

/**
 * 该Fragment用来处理星球的点击事件
 */
public class Fragment_Star extends Fragment {

    private static final String TAG = "Fragment_Star";
    private ImageView mComplain_iv;
    private ImageView mFriends_iv;
    private ImageView mOther_iv;
    private ImageView mLove_iv;
    private static final int  TYPE_COMPLAIN = 1;
    private static final int  TYPE_FRIEND = 2;
    private static final int  TYPE_LOVE = 3;
    private static final int  TYPE_OTHER = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_star, container, false);
        mComplain_iv = rootView.findViewById(R.id.complain_iv);
        mFriends_iv = rootView.findViewById(R.id.friends_iv);
        mOther_iv = rootView.findViewById(R.id.other_iv);
        mLove_iv = rootView.findViewById(R.id.love_iv);
        //设定监听事件
        initListener();
        return rootView;
    }
    //初始化监听事件
    private void initListener() {
        //吐槽按钮的点击事件
        mComplain_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d(TAG,"mComplain_iv was onClicked...");
               Intent intent = new Intent(getActivity(), StarActivity.class);
               intent.putExtra("posttype",TYPE_COMPLAIN);
               startActivity(intent);
            }
        });
        //交友按钮的点击事件
        mFriends_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"mFriends_iv was onClicked...");
                Intent intent = new Intent(getActivity(), StarActivity.class);
                intent.putExtra("posttype",TYPE_FRIEND);
                startActivity(intent);
            }
        });
        //表白按钮的点击事件
        mLove_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"mLove_iv was onClicked...");
                Intent intent = new Intent(getActivity(), StarActivity.class);
                intent.putExtra("posttype",TYPE_LOVE);
                startActivity(intent);
            }
        });
        //其他按钮的点击事件
        mOther_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"mOther_iv was onClicked...");
                Intent intent = new Intent(getActivity(), StarActivity.class);
                intent.putExtra("posttype",TYPE_OTHER);
                startActivity(intent);
            }
        });
    }
}
