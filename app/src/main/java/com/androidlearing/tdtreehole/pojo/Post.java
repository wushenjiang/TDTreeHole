package com.androidlearing.tdtreehole.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: Post
 * @Description: 对应Post表结构
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/19 12:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/19 12:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Post implements Parcelable {

    public Post() {
    }

    protected Post(Parcel in) {
        postid = in.readInt();
        postcontent = in.readString();
        modifytime = in.readString();
        posttime = in.readString();
        title = in.readString();
        userid = in.readInt();
        posttype = in.readString();
        username = in.readString();
        likes = in.readInt();
        comments = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    private int postid;
    private String postcontent;
    private String modifytime;
    private String posttime;
    private String title;
    private int userid;
    private String posttype;
    private String username;
    private Integer likes;
    private Integer comments;

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postid=" + postid +
                ", postcontent='" + postcontent + '\'' +
                ", modifytime='" + modifytime + '\'' +
                ", posttime='" + posttime + '\'' +
                ", title='" + title + '\'' +
                ", userid=" + userid +
                ", posttype='" + posttype + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(postid);
        dest.writeString(postcontent);
        dest.writeString(modifytime);
        dest.writeString(posttime);
        dest.writeString(title);
        dest.writeInt(userid);
        dest.writeString(posttype);
        dest.writeString(username);
        dest.writeInt(likes);
        dest.writeInt(comments);
    }
}
