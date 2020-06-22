package com.androidlearing.tdtreehole.pojo;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.pojo
 * @ClassName: User
 * @Description: 对应User表结构
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/22 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/22 11:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class User {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int id;
    private String username;
    private String password;
    private String repassword;

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
