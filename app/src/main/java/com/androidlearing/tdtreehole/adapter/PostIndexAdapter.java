package com.androidlearing.tdtreehole.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.bean.ItemBean;

import java.util.List;

/**
 * @ProjectName: RecyclerViewDemo
 * @Package: com.androidlearing.recyclerviewdemo.adapter
 * @ClassName: StaggerAdapter
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/3/29 22:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/29 22:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PostIndexAdapter extends RecyclerViewBaseAdapter {

    public PostIndexAdapter(List<ItemBean> data) {
        super(data);
    }
    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_post_index, null);
        return view;
    }

}
