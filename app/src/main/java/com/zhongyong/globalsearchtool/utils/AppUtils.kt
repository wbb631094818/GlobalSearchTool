package com.zhongyong.globalsearchtool.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import com.github.promeg.pinyinhelper.Pinyin
import com.zhongyong.globalsearchtool.search.bean.SearchInfo


/**
 *  第三方APP相关工具类（不是自己的APP，管别人的）
 */
public object AppUtils {


    /**
     *  通过包名，打开APP
     */
    public fun openApp(context: Context, id: String) {
        val intent: Intent? = context.getPackageManager().getLaunchIntentForPackage(id)
        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    /**
     *  获取所以的APP信息
     */
    public fun getPkgListNew(context: Context): List<SearchInfo> {
        val packages: MutableList<SearchInfo> = ArrayList()
        try {
            val packageInfos: List<PackageInfo> = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_ACTIVITIES or
                        PackageManager.GET_SERVICES
            )
            var searchInfo:SearchInfo;
            for (info in packageInfos) {
//                val pkg = info.packageName
//                val icon = info.applicationInfo.loadIcon(context.getPackageManager());
                val name = context.getPackageManager().getApplicationLabel(info.applicationInfo)
                    .toString();
                val pinyin = Pinyin.toPinyin(name,"")
//                Log.e("wbb", "pinyin: " +pinyin)
                searchInfo = SearchInfo();
                searchInfo.name = name;
                searchInfo.packageId = info.packageName;
                searchInfo.appInfo = "版本:" + info.versionName;
                searchInfo.pinyin = pinyin;
                searchInfo.type = "app";
                packages.add(searchInfo)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            Log.e("wbb", "getPkgListNew: " + t.message)
        }
        return packages
    }


    public fun getIcon(mContext: Context, pakgename: String): Drawable? {
        val pm: PackageManager = mContext.getPackageManager()
        try {
            val appInfo = pm.getApplicationInfo(pakgename, PackageManager.GET_META_DATA)
            // 应用名称
            // pm.getApplicationLabel(appInfo)
            //应用图标
            return pm.getApplicationIcon(appInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }
}