package com.zhongyong.globalsearchtool.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import com.github.promeg.pinyinhelper.Pinyin
import com.zhongyong.globalsearchtool.search.bean.SearchInfo


/**
 *  第三方APP相关工具类（不是自己的APP，管别人的）
 */
public object AppUtils {


    /**
     *  打开浏览器
     */
    public fun openWeb(context: Context, url: String){
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 打开浏览器
     */
    public fun openWeb(context: Context,packageName: String, className:String,url: String){
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClassName(packageName,className);
        context.startActivity(intent)

    }

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
                val pinyin = Pinyin.toPinyin(name, "")
//                Log.e("wbb", "pinyin: " +pinyin)
                searchInfo = SearchInfo();
                searchInfo.name = name;
                searchInfo.packageId = info.packageName;
                searchInfo.appInfo = "版本:" + info.versionName;
                searchInfo.pinyin = pinyin;
                searchInfo.type = "app";
                searchInfo.diy = 0;
                packages.add(searchInfo)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            Log.e("wbb", "getPkgListNew: " + t.message)
        }
        return packages
    }

    /**
     * 获取系统安装的所有的浏览器应用
     *
     * @param context
     */
    fun getAllBrows(context: Context?, searchText:String) : List<SearchInfo>{
        val packages: MutableList<SearchInfo> = ArrayList()
        val uri = Uri.parse("http://www.baidu.com")
        val it = Intent(Intent.ACTION_VIEW, uri)
        // 通过查询，获得所有ResolveInfo对象.
        val resolveInfos:List<ResolveInfo> = context?.packageManager?.queryIntentActivities(it, PackageManager.MATCH_DEFAULT_ONLY) as List<ResolveInfo>
        var searchInfo:SearchInfo;
        val lastBrowser = AppPreferencesUtils.getLastDefultBrowser();
        val defultSearchEngine: String? = AppPreferencesUtils.getDefultSearchEngine();
        for (resolveInfo in resolveInfos) {
            val name = context.getPackageManager().getApplicationLabel(resolveInfo.activityInfo.applicationInfo)
                .toString();
//            val pinyin = Pinyin.toPinyin(name, "")
            searchInfo = SearchInfo();
            searchInfo.webUrl = Utils.getSearchWebUrl(defultSearchEngine,searchText)
            searchInfo.name = "使用"+name+defultSearchEngine+"搜索: "+searchText;
            searchInfo.packageId = resolveInfo.activityInfo.packageName;
//            searchInfo.pinyin = pinyin;
            searchInfo.type = "web";
            searchInfo.webClass = resolveInfo.activityInfo.name
            if (searchInfo.packageId.equals(lastBrowser)){
                packages.add(0,searchInfo)
            }else {
                packages.add(searchInfo)
            }
        }
        return packages
    }
    /**
     * 获取系统安装的所有的浏览器应用
     *
     * @param context
     */
    fun getAllBrows(context: Context?) : List<SearchInfo>{
        val packages: MutableList<SearchInfo> = ArrayList()
        val uri = Uri.parse("http://www.baidu.com")
        val it = Intent(Intent.ACTION_VIEW, uri)
        // 通过查询，获得所有ResolveInfo对象.
        val resolveInfos:List<ResolveInfo> = context?.packageManager?.queryIntentActivities(it, PackageManager.MATCH_DEFAULT_ONLY) as List<ResolveInfo>
        var searchInfo:SearchInfo;
        for (resolveInfo in resolveInfos) {
            val name = context.getPackageManager().getApplicationLabel(resolveInfo.activityInfo.applicationInfo)
                .toString();
//            val pinyin = Pinyin.toPinyin(name, "")
            searchInfo = SearchInfo();
            searchInfo.packageId = resolveInfo.activityInfo.packageName;
//            searchInfo.pinyin = pinyin;
            searchInfo.name = name;
            searchInfo.type = "web";
            searchInfo.webClass = resolveInfo.activityInfo.name
            packages.add(searchInfo)
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