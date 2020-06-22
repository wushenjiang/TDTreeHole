package com.androidlearing.tdtreehole.pojo;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: Likes
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/27 21:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/27 21:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Likes {
    private Integer id;
    private Integer postid;
    private Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + id +
                ", postid=" + postid +
                ", userid=" + userid +
                '}';
    }
}
