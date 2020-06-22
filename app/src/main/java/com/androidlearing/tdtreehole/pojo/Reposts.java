package com.androidlearing.tdtreehole.pojo;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: Reposts
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/22 21:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 21:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Reposts {
    private Post post;
    private Repost rePost;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Repost getRePost() {
        return rePost;
    }

    public void setRePost(Repost rePost) {
        this.rePost = rePost;
    }
}
