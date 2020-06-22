package com.androidlearing.tdtreehole.pojo;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: Repost
 * @Description: Repost表结构
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/19 21:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/19 21:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Repost {
    public int getRepostid() {
        return repostid;
    }

    public void setRepostid(int repostid) {
        this.repostid = repostid;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int repostid;
    private String modifytime;
    private String publishtime;
    private int postid;
    private int userid;
    private String content;
    private String username;

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
