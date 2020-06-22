package com.androidlearing.tdtreehole.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @ProjectName: TDTreeHole
 * @Package: com.androidlearing.tdtreehole.databasehelper
 * @ClassName: DataBaseHelper
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/4/23 16:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/23 16:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DataBaseHelper  extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "user";
    private static final int VERSION = 1;
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }
    //创建数据库的回调
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"创建数据库...");
        String sql = "create table "+DATA_BASE_NAME+"(id integer primary key,username varchar(255),password varchar(255))";
        db.execSQL(sql);
    }
    //需要修改表结构时使用该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库的回调

    }
}
