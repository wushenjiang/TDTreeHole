package com.androidlearing.tdtreehole.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.bean.ItemBean;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

/**
 * @ProjectName: RecyclerViewDemo
 * @Package: com.androidlearing.recyclerviewdemo.adapter
 * @ClassName: RecyclerViewBaseAdapter
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/3/29 21:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/29 21:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected  static List<ItemBean> mData;
    private onItemClickListener mOnItemClickListener;


    public RecyclerViewBaseAdapter(List<ItemBean> data)
    {
        this.mData = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getSubView(parent,viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 这个方法是用于绑定holder的，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //在这里设置数据
        ((InnerHolder) holder).setData(mData.get(position),position);
    }

    /**
     * 返回条目的个数
     * @return
     */
    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(onItemClickListener Listener) {
        //设置一个监听，其实就是要设置一个接口，一个回调的接口
        this.mOnItemClickListener = Listener;
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

    //获取随机颜色
    public static int getRandColor(){
        int R=0,G=0,B=0;
        Random random = new Random();
        for (int i = 0;i<2;i++){
            int temp = random.nextInt(16);
            R = R*16+temp;
            temp = random.nextInt(16);
            G = G*16+temp;
            temp = random.nextInt(16);
            B = B*16+temp;
        }
        //设置透明度为100%
        return Color.argb(100,R,G,B);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mContent;
        private int mPosition;
        private final TextView mPostTime;
        private final TextView mLikes;
        private final TextView mComments;

        public InnerHolder( View itemView) {
            super(itemView);
            //给cardview随机设置背景颜色
            itemView.findViewById(R.id.cv_post).setBackgroundColor(getRandColor());
            //找到条目控件
            mTitle = itemView.findViewById(R.id.title_tv);
            mContent = itemView.findViewById(R.id.content_tv);
            mPostTime = itemView.findViewById(R.id.post_time_tv);
            mLikes = itemView.findViewById(R.id.likes_tv);
            mComments = itemView.findViewById(R.id.comments_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });

        }

        /**
         * 这个方法用于设置数据
         * @param itemBean
         */
        public void setData(ItemBean itemBean,int position) {
            this.mPosition = position;
            //开始设置数据
            mTitle.setText(itemBean.getTitle());
            mContent.setText(itemBean.getContent());
            mPostTime.setText(itemBean.getPosttime());
            mComments.setText(String.valueOf(itemBean.getComments()));
            mLikes.setText(String.valueOf(itemBean.getLikes()));

        }
    }
}
