package com.zhongyong.globalsearchtool.search.manager

import android.app.Activity
import android.content.Context
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppUtils

/**
 *  搜索相关的
 */
object SearchManager {

    public fun searchItemClick(context: Context,searchInfo: SearchInfo){
        if (searchInfo.type.equals("app")){
            // 点击跳转
            AppUtils.openApp(context, searchInfo.packageId)
        }


        // 跳转后结束搜索页面
        val activity = context as Activity;
        activity.finish()
    }

}