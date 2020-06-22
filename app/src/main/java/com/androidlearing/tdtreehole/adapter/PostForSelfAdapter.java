package com.androidlearing.tdtreehole.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.bean.ItemSelfPostBean;

import java.util.List;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.adapter
 * @ClassName: PostForSelfAdapter
 * @Description: 我的回帖模块适配器
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/22 19:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 19:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PostForSelfAdapter extends RecyclerView.Adapter {
    //定义一个list来存储数据
    private final List<ItemSelfPostBean> mData;
    private PostForSelfAdapter.onItemClickListener mOnItemClickListener;

    public PostForSelfAdapter(List<ItemSelfPostBean> List){
        mData = List;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_my_send,null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //在这里设置数据
        ((InnerHolder )holder).setData(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }
    public void setOnItemClickListener(PostForSelfAdapter.onItemClickListener Listener) {
        //设置一个监听，其实就是要设置一个接口，一个回调的接口
        this.mOnItemClickListener =  Listener;
    }

    /**
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部的方法
     * 3.提供设置接口的方法(外部实现)
     * 4.接口调用
     */
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    private class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mMy_post_title;
        private final TextView mMy_post_type;
        private final TextView mMy_post_content;
        private final TextView mMy_post_time;
        private int mPosition;

        public InnerHolder(View view) {
            super(view);
            //找到条目控件
            mMy_post_title = view.findViewById(R.id.my_post_title);
            mMy_post_type = view.findViewById(R.id.my_post_type);
            mMy_post_content = view.findViewById(R.id.my_post_content);
            mMy_post_time = view.findViewById(R.id.my_post_time);
            //设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });

        }
        //设置数据
        public void setData(ItemSelfPostBean itemSelfPostBean, int position) {
            this.mPosition = position;
            //开始设置数据
            mMy_post_title.setText(mData.get(position).getTitle());
            mMy_post_type.setText(mData.get(position).getType());
            mMy_post_content.setText(mData.get(position).getContent());
            mMy_post_time.setText(mData.get(position).getTime());

        }
    }
}
