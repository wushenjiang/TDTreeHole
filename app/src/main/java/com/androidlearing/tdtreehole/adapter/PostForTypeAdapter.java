package com.androidlearing.tdtreehole.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.bean.ItemBean;

import java.util.List;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.adapter
 * @ClassName: PostForTypeAdapter
 * @Description: 星球模块的适配器
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/21 19:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/21 19:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PostForTypeAdapter extends RecyclerViewBaseAdapter {
    public PostForTypeAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_post_for_type,null);
        return view;
    }
}
