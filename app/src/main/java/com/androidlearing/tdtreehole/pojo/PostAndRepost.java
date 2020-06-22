package com.androidlearing.tdtreehole.pojo;

import com.androidlearing.tdtreehole.bean.ItemBean;
import com.androidlearing.tdtreehole.bean.ItemBeanRepost;

import java.util.List;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: PostAndRepost
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/19 23:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/19 23:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PostAndRepost {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    private int type;
    private ItemBean post;
    private ItemBeanRepost reposts;

    public ItemBean getPost() {
        return post;
    }

    public void setPost(ItemBean post) {
        this.post = post;
    }

    public ItemBeanRepost getReposts() {
        return reposts;
    }

    public void setReposts(ItemBeanRepost reposts) {
        this.reposts = reposts;
    }
}
