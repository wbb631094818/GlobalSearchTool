package com.zhongyong.globalsearchtool.search.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  搜索的APP相关信息
 */
@Entity(tableName = "appinfo")
class SearchInfo {

//    constructor(name: String, packageId: String, appInfo: String) {
//        this.name = name;
//        this.packageId = packageId;
//        this.appInfo = appInfo;
////        this.icon = icon;
//    }

    @PrimaryKey(autoGenerate = true)
    var id = 0;

    @ColumnInfo(name = "name")
    var name = "";

    @ColumnInfo(name = "packageId")
    var packageId = ""; // 应用包名

    @ColumnInfo(name = "appInfo")
    var appInfo = ""; // App 信息

    @ColumnInfo(name = "pinyin")
    var pinyin = ""; // 应用名称拼音

    @ColumnInfo(name = "type")
    var type = ""; // type  app 为打开app   web 打开网页   calculator 计算器
    
//    @ColumnInfo(name = "icon")
//    var icon: Drawable? = null; // 图标



}