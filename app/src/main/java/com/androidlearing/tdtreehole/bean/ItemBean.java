package com.androidlearing.tdtreehole.bean;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.bean
 * @ClassName: ItemBean
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/19 13:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/19 13:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ItemBean {
    private String title;
    private String content;
    private String username;
    private String posttime;
    private String posttype;
    private Integer likes;
    private Integer comments;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }
}
