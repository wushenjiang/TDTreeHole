package com.androidlearing.tdtreehole.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.database
 * @ClassName: Dao
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/23 16:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 16:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Dao {

    private static final String TAG ="Dao" ;
    private final DataBaseHelper mHelper;
    private static final String DATA_BASE_NAME = "user";

    //创建数据库
    public Dao(Context context){
        mHelper = new DataBaseHelper(context);
    }
    //插入数据
    public void insert(int id,String username,String password){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("username",username);
        values.put("password",password);
        db.insert(DATA_BASE_NAME,null,values);
        db.close();
    }
    //查询用户id
    public int select_id(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int userid = 0;
        Cursor cursor = db.query(DATA_BASE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            userid = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        db.close();
        return userid;
    }
    //查询用户名
    public String select_username(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String username = "";
        Cursor cursor = db.query(DATA_BASE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            username = cursor.getString(cursor.getColumnIndex("username"));
        }
        cursor.close();
        db.close();
        return username;
    }
    //查询用户密码
    public String select_password(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String username = "";
        Cursor cursor = db.query(DATA_BASE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            username = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        db.close();
        return username;
    }
    //删除数据
    public void delete(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DATA_BASE_NAME,null,null);
        db.close();
    }
}
