package com.androidlearing.tdtreehole.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.tdtreehole.R;
import com.androidlearing.tdtreehole.pojo.Post;
import com.androidlearing.tdtreehole.pojo.PostAndRepost;
import com.androidlearing.tdtreehole.pojo.Repost;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.adapter
 * @ClassName: PostAndRepostAdapter
 * @Description: 看帖模块的适配器
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/19 23:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/19 23:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PostAndRepostAdapter extends RecyclerView.Adapter {
    private static final String TAG = "PostAndRepostAdapter";
    //定义两个List变量
    private final List<PostAndRepost> mData;
    //定义两个常量标识,因为有两种类型
    public static final int TYPE_POST_CONTENT = 0;
    public static final int TYPE_REPOST_CONTENT = 1;
    //使用构造方法获得数据
    public PostAndRepostAdapter(List<PostAndRepost> data){
        this.mData = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这里去创建ViewHolder
        View view;
        if(viewType == TYPE_POST_CONTENT){
            view = View.inflate(parent.getContext(), R.layout.item_repost_postcontent,null);
            return new PostContentViewHolder(view);
        }else{
            view = View.inflate(parent.getContext(),R.layout.item_repost_repostcontent,null);
            return new RepostContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //根据条目类型绑定数据
        if(holder instanceof PostContentViewHolder){
            PostContentViewHolder postContentViewHolder = (PostContentViewHolder) holder;
            postContentViewHolder.mUsername.setText(mData.get(position).getPost().getUsername());
            postContentViewHolder.mPostContent.setText(mData.get(position).getPost().getContent());
            postContentViewHolder.mPostTime.setText(mData.get(position).getPost().getPosttime());
            postContentViewHolder.mPostType.setText(mData.get(position).getPost().getPosttype());
        }else if(holder instanceof RepostContentViewHolder){
            RepostContentViewHolder viewHolder = (RepostContentViewHolder) holder;
            viewHolder.mRepostUsername.setText(mData.get(position).getReposts().getName());
            viewHolder.mRepostTime.setText(mData.get(position).getReposts().getPosttime());
            viewHolder.mRepostContent.setText(mData.get(position).getReposts().getContent());

        }
    }
    //这里返回条目数量
    @Override
    public int getItemCount() {
        return mData.size();
    }
    //复写该方法来根据条件取得条目种类
    @Override
    public int getItemViewType(int position) {
        PostAndRepost postAndRepost = mData.get(position);
        if(postAndRepost.getType() ==TYPE_POST_CONTENT){
            return TYPE_POST_CONTENT;
        }else{
            return TYPE_REPOST_CONTENT;
        }
    }

    private class PostContentViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUsername;
        private final TextView mPostContent;
        private final TextView mPostTime;
        private final TextView mPostType;

        public PostContentViewHolder(View view) {
            super(view);
            //找到控件
            mUsername = view.findViewById(R.id.user_name_tv);
            mPostTime = view.findViewById(R.id.post_time_tv);
            mPostContent = view.findViewById(R.id.post_content_tv);
            mPostType = view.findViewById(R.id.type_tv);
        }
    }

    private class RepostContentViewHolder extends RecyclerView.ViewHolder {


        private final TextView mRepostUsername;
        private final TextView mRepostTime;
        private final TextView mRepostContent;

        public RepostContentViewHolder(View view) {
            super(view);
            //找到控件
            mRepostUsername = view.findViewById(R.id.repost_user_name_tv);
            mRepostTime = view.findViewById(R.id.reposts_content_time_tv);
            mRepostContent = view.findViewById(R.id.reposts_content_tv);
        }
    }

}
