package com.zhongyong.globalsearchtool.search.manager

import android.content.Context
import com.zhongyong.globalsearchtool.application.SearchApplication
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils
import com.zhongyong.globalsearchtool.utils.AppUtils

/**
 *  搜索相关的
 */
object SearchManager {

    public fun searchItemClick(context: Context,searchInfo: SearchInfo){
        if (searchInfo.type.equals("app")){
            // 点击跳转
            AppUtils.openApp(context, searchInfo.packageId)
        }else if (searchInfo.type.equals("web")){
            // 保存上次的
            AppUtils.openWeb(context,searchInfo.packageId,searchInfo.webClass,searchInfo.webUrl)
            AppPreferencesUtils.setLastDefultBrowser(searchInfo.packageId)
        }

        // 跳转后结束搜索页面
//        val activity = context as Activity;
//        activity.finish()
    }


    public fun addWebSearch(searchText:String,infos:ArrayList<SearchInfo>){
        infos.addAll(AppUtils.getAllBrows(SearchApplication.getApplication(),searchText))
    }

}