package com.zhongyong.globalsearchtool.application

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import com.zhongyong.globalsearchtool.db.DbManager

/**
 *  全局application
 */
public class SearchApplication:Application(){

    // 静态变量与方法
    companion object{
        private var searchApplication: SearchApplication? = null
        public  fun getApplication(): Context? {
            return searchApplication?.applicationContext
        }
        public fun getSearchApplication():SearchApplication?{
            return searchApplication;
        }
    }



    override fun onCreate() {
        super.onCreate()
        searchApplication = this;

        MMKV.initialize(this)
        // 初始化数据库
        DbManager.initDbData()
    }
}