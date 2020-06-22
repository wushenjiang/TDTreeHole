package com.androidlearing.tdtreehole.bean;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.bean
 * @ClassName: ItemSelfBean
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/22 19:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 19:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ItemSelfPostBean {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String title;
    private String username;
    private String type;
    private String content;
    private String time;
}
